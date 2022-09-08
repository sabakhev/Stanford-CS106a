
/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {
	private JTextField name;
	private JButton add;
	private JButton delete;
	private JButton lookup;

	private JTextField status;
	private JButton statusButton;
	private JTextField picture;
	private JButton pictureButton;
	private JTextField friend;
	private JButton friendButton;

	private FacePamphletDatabase database = new FacePamphletDatabase();
	private FacePamphletProfile currentProfile = null;
	private FacePamphletCanvas canvas;

	private static final int START_OF_CHARACTERS = 33;
	private static final int END_OF_CHARACTERS = 126;

	/**
	 * This method has the responsibility for initializing the interactors in the
	 * application, and taking care of any other initialization that needs to be
	 * performed.
	 */
	public void init() {
		addActionListeners();
		addComponentsNorth();
		addComponentsWest();
		canvas = new FacePamphletCanvas();
		add(canvas);
	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {
		// *
		if (e.getSource() == add) {
			addingProfile();
		}
		// *
		if (e.getSource() == delete) {
			deletingProfile();
		}
		// *
		if (e.getSource() == lookup) {
			lookingUpProfile();
		}
		// *
		if (e.getSource() == status || e.getSource() == statusButton) {
			updatingProfileStatus();
		}
		// *
		if (e.getSource() == picture || e.getSource() == pictureButton) {
			updatingProfilePicture();
		}
		// *
		if (e.getSource() == friend || e.getSource() == friendButton) {
			updatingProfileFriends();
		}
	}

	// * This method simply adds the JButons, JTextfield and a JLabel on the North
	// of the canvas. It also adds action listeners to appropriate components.
	private void addComponentsNorth() {

		JLabel label = new JLabel("Name");
		name = new JTextField(TEXT_FIELD_SIZE);
		add = new JButton("Add");
		add.addActionListener(this);
		delete = new JButton("Delete");
		delete.addActionListener(this);
		lookup = new JButton("Lookup");
		lookup.addActionListener(this);

		add(label, NORTH);
		add(name, NORTH);
		add(add, NORTH);
		add(delete, NORTH);
		add(lookup, NORTH);
	}

	// * The purpose of this method is also to add some of the components, but on
	// the West side of the canvas. Also, action listeners are added to some of
	// them.
	private void addComponentsWest() {
		status = new JTextField(TEXT_FIELD_SIZE);
		status.addActionListener(this);
		statusButton = new JButton("Change Status");
		statusButton.addActionListener(this);

		picture = new JTextField(TEXT_FIELD_SIZE);
		picture.addActionListener(this);
		pictureButton = new JButton("Change picture");
		pictureButton.addActionListener(this);

		friend = new JTextField(TEXT_FIELD_SIZE);
		friend.addActionListener(this);
		friendButton = new JButton("add friend");
		friendButton.addActionListener(this);

		add(status, WEST);
		add(statusButton, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		add(picture, WEST);
		add(pictureButton, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		add(friend, WEST);
		add(friendButton, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

	}

	// * If Add button is clicked, this method is responsible for taking actions. It
	// checks if the name typed by user already corresponds to an existing profile.
	// If not, a new profile is created and displayed on canvas.
	private void addingProfile() {
		String typedName = name.getText();
		if (!isItEmpty(typedName)) {
			if (database.containsProfile(typedName)) {
				canvas.displayProfile(database.getProfile(typedName));
				canvas.showMessage("Profile for " + typedName + " already exists.");
			} else {
				FacePamphletProfile newProfile = new FacePamphletProfile(typedName);
				database.addProfile(newProfile);
				canvas.displayProfile(newProfile);
				canvas.showMessage("New profile created");
			}
			currentProfile = database.getProfile(typedName);
			name.setText("");
		}
	}

	// * If Delete button is clicked, canvas is cleared from all the information and
	// program checks if the profile name given by user exists. If so, it deletes
	// that profile and if not, an appropriate message is printed on canvas.
	private void deletingProfile() {
		String typedName = name.getText();
		if (!isItEmpty(typedName)) {
			canvas.removeAll();
			if (!database.containsProfile(typedName)) {
				canvas.showMessage("Profile with name " + typedName + " does not exist");
			} else {
				canvas.showMessage("Profile of " + typedName + " was deleted");
				database.deleteProfile(typedName);
			}
			currentProfile = null;
			name.setText("");
		}
	}

	// * If Lookup button is clicked, first operations are the same as the
	// deletingProfile method. If given profile exists, it is displayed on canvas,
	// if not, user is informed.
	private void lookingUpProfile() {
		String typedName = name.getText();
		if (!isItEmpty(typedName)) {
			canvas.removeAll();
			if (!database.containsProfile(typedName)) {
				canvas.showMessage("Profile with name " + typedName + " does not exist");
				currentProfile = null;
			} else {
				canvas.displayProfile(database.getProfile(typedName));
				canvas.showMessage("Displaying " + typedName);
				currentProfile = database.getProfile(typedName);
			}
			name.setText("");
		}
	}

	// * If user wants to update profile status this method is called. It checks if
	// current profile is selected, then changes given profile status and displays
	// that profile on canvas. If current profile isn't selected user gets informed.
	private void updatingProfileStatus() {
		String newStatus = status.getText();
		if (!isItEmpty(newStatus)) {
			if (currentProfile != null) {
				database.getProfile(currentProfile.getName()).setStatus(newStatus);
				canvas.displayProfile(database.getProfile(currentProfile.getName()));
				canvas.showMessage("Status updated to: " + newStatus);
			} else {
				canvas.showMessage("Please select a profile to change status");
			}
			status.setText("");
		}
	}

	// * If user wants to update profile picture, this method is called. It checks
	// if current profile is selected.If not, appropriate message is shown. If so,
	// it updates the picture and displays the changed profile on canvas.
	// If the picture can't be updated and error is thrown, user is also informed.
	private void updatingProfilePicture() {
		String fileName = picture.getText();
		if (!isItEmpty(fileName)) {
			if (currentProfile != null) {
				GImage image = null;
				try {
					image = new GImage(fileName);
					database.getProfile(currentProfile.getName()).setImage(image);
					canvas.displayProfile(database.getProfile(currentProfile.getName()));
					canvas.showMessage("Picture updated");
				} catch (ErrorException ex) {
					canvas.displayProfile(database.getProfile(currentProfile.getName()));
					canvas.showMessage("Unable to open image file: " + fileName);
				}
			} else {
				canvas.showMessage("Please select a profile to change picture");
			}
			picture.setText("");
		}
	}

	// * If user wants to add a friend, this method is called. The procedure and
	// structure is almost the same as previous two methods : checking for current
	// profile, whether or not the name corresponds to an existing profile, if it is
	// the same as current profile name or if it already is in current profile's
	// friend's list.
	private void updatingProfileFriends() {
		String newFriend = friend.getText();
		if (!isItEmpty(newFriend)) {
			if (currentProfile != null) {
				if (database.containsProfile(newFriend)) {
					if (currentProfile.getName().equals(newFriend)) {
						canvas.showMessage("Users can't befriend themselves");
					} else {
						if (database.getProfile(currentProfile.getName()).addFriend(newFriend)) {
							database.getProfile(newFriend).addFriend(currentProfile.getName());
							canvas.displayProfile(database.getProfile(currentProfile.getName()));
							canvas.showMessage(newFriend + " was added as a friend");
						} else {
							canvas.showMessage(currentProfile.getName() + " already has " + newFriend + " as a friend");
						}
					}
				} else {
					canvas.showMessage("The profile of " + newFriend + " does not exist");
				}
			} else {
				canvas.showMessage("Please select a profile to add friend");
			}
			friend.setText("");
		}
	}

	// * This method determines if the given string contains actual characters or
	// not. It is called every time user enters something in the text field.
	private boolean isItEmpty(String str) {
		if (str.isEmpty()) {
			return true;
		}
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) >= START_OF_CHARACTERS & str.charAt(i) <= END_OF_CHARACTERS) {
				return false;
			}
		}
		return true;
	}
}
