
import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class ExtendedFacePamphletCanvas extends GCanvas implements FacePamphletConstants {
	

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * display
	 */
	public ExtendedFacePamphletCanvas() {

	}

	/**
	 * This method displays a message string near the bottom of the canvas. Every
	 * time this method is called, the previously displayed message (if any) is
	 * replaced by the new message text passed in.
	 */
	public void showMessage(String msg) {
		GLabel message = new GLabel(msg);
		message.setFont(MESSAGE_FONT);
		double message_x = getWidth() / 2 - message.getWidth() / 2;
		double message_y = getHeight() - BOTTOM_MESSAGE_MARGIN;
		while (getElementAt(getWidth() / 2, message_y) != null) {
			remove(getElementAt(getWidth() / 2, message_y));
		}
		add(message, message_x, message_y);
	}

	/**
	 * This method displays the given profile on the canvas. The canvas is first
	 * cleared of all existing items (including messages displayed near the bottom
	 * of the screen) and then the given profile is displayed. The profile display
	 * includes the name of the user from the profile, the corresponding image (or
	 * an indication that an image does not exist), the status of the user, and a
	 * list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile, FacePamphletDatabase database) {
		removeAll();
		addProfileName(profile);
		addProfilePicture(profile);
		addProfileStatus(profile);
		addProfileFriends(profile);
		addPeopleInCommon(profile, database);
	}

	private void addProfileName(FacePamphletProfile profile) {
		GLabel ProfileName = new GLabel(profile.getName());
		ProfileName.setFont(MESSAGE_FONT);
		double ProfileName_x = LEFT_MARGIN;
		double ProfileName_y = TOP_MARGIN;
		add(ProfileName, ProfileName_x, ProfileName_y);
	}

	private void addProfilePicture(FacePamphletProfile profile) {
		GImage profilePicture = profile.getImage();
		double image_x = LEFT_MARGIN;
		double image_y = TOP_MARGIN + IMAGE_MARGIN;
		if (profilePicture != null) {
			double sx = IMAGE_WIDTH / profilePicture.getWidth();
			double sy = IMAGE_HEIGHT / profilePicture.getHeight();
			profilePicture.scale(sx, sy);
			add(profilePicture, image_x, image_y);
		} else {
			GRect imageRect = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(imageRect, image_x, image_y);

			GLabel imageLabel = new GLabel("No Image");
			imageLabel.setFont(PROFILE_IMAGE_FONT);
			double imageLabel_x = image_x + IMAGE_WIDTH / 2 - imageLabel.getWidth() / 2;
			double imageLabel_y = image_y + IMAGE_HEIGHT / 2 - imageLabel.getHeight() / 2;
			add(imageLabel, imageLabel_x, imageLabel_y);
		}
	}

	private void addProfileStatus(FacePamphletProfile profile) {
		double status_x = LEFT_MARGIN;
		double status_y = TOP_MARGIN + IMAGE_MARGIN + +IMAGE_HEIGHT + STATUS_MARGIN;
		if (profile.getStatus().isEmpty()) {
			GLabel noStatus = new GLabel("No current status");
			noStatus.setFont(PROFILE_STATUS_FONT);
			add(noStatus, status_x, status_y);
		} else {
			GLabel profileStatus = new GLabel(profile.getName() + " is " + profile.getStatus());
			profileStatus.setFont(PROFILE_STATUS_FONT);
			add(profileStatus, status_x, status_y);
		}
	}

	private void addProfileFriends(FacePamphletProfile profile) {
		double profileFriendsLabel_x = getWidth() / 2;
		double profileFriendsLabel_y = TOP_MARGIN + IMAGE_MARGIN;
		GLabel profileFriendsLabel = new GLabel("Friends");
		profileFriendsLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(profileFriendsLabel, profileFriendsLabel_x, profileFriendsLabel_y);
		Iterator<String> friendsIterator = profile.getFriends();
		double y_offset = profileFriendsLabel.getHeight() * (3 / 2);
		while (friendsIterator.hasNext()) {
			GLabel friendsListLabel = new GLabel(friendsIterator.next());
			friendsListLabel.setFont(PROFILE_FRIEND_FONT);
			add(friendsListLabel, profileFriendsLabel_x, profileFriendsLabel_y + y_offset);
			y_offset = y_offset + profileFriendsLabel.getHeight() * (3 / 2);
		}
	}

	private void addPeopleInCommon(FacePamphletProfile profile, FacePamphletDatabase database) {
		ArrayList<String> peopleInCommon = peopleInCommon(profile, database);
		double peopleInCommonLabel_x = getWidth() / 2;
		double peopleInCommonLabel_y = TOP_MARGIN + IMAGE_MARGIN + getHeight() / 2;
		GLabel peopleInCommonLabel = new GLabel("People in common");
		peopleInCommonLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(peopleInCommonLabel, peopleInCommonLabel_x, peopleInCommonLabel_y);
		double y_offset = peopleInCommonLabel.getHeight() * (3 / 2);
		for (int i = 0; i < peopleInCommon.size(); i++) {
			GLabel peopleInCommonListLabel = new GLabel(peopleInCommon.get(i));
			peopleInCommonListLabel.setFont(PROFILE_FRIEND_FONT);
			add(peopleInCommonListLabel, peopleInCommonLabel_x, peopleInCommonLabel_y + y_offset);
			y_offset = y_offset + peopleInCommonLabel.getHeight() * (3 / 2);
		}
	}

	// * This method takes in a profile from database and looks for other profiles
	// that have some friends in common. It returns ArrayList of such friends.
	private ArrayList<String> peopleInCommon(FacePamphletProfile profile, FacePamphletDatabase database) {
		ArrayList<String> array = new ArrayList<>();
		Iterator<String> friendsIterator = profile.getFriends();
		while (friendsIterator.hasNext()) {
			Iterator<String> friendsFriends = database.getProfile(friendsIterator.next()).getFriends();
			int matches;
			while (friendsFriends.hasNext()) {
				matches = 0;
				String possibleFriend = friendsFriends.next();
				Iterator<String> friendsIterator2 = profile.getFriends();
				while (friendsIterator2.hasNext()) {
					Iterator<String> friendsFriends2 = database.getProfile(friendsIterator2.next()).getFriends();
					while (friendsFriends2.hasNext()) {
						String possibleFriend2 = friendsFriends2.next();
						if (possibleFriend.equals(possibleFriend2)) {
							boolean hasAsFriend = false;
							Iterator<String> friendsIterator3 = profile.getFriends();
							while (friendsIterator3.hasNext()) {
								if (possibleFriend2.equals(friendsIterator3.next())) {
									hasAsFriend = true;
								}
							}
							if (!hasAsFriend & !(array.contains(possibleFriend2))
									& !database.getProfile(possibleFriend2).getName().equals(profile.getName())) {
								matches++;
							}
						}
					}
				}
				if (matches >= 1 & !array.contains(possibleFriend + " ( " + matches + " mutual friends )")) 
					array.add(possibleFriend + " ( " + matches + " mutual friends )");
			}
		}
		return array;
	}

}

