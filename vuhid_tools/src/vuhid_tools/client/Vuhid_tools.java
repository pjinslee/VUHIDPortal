//BLAHBLAHBLAH

package vuhid_tools.client;

import java.util.ArrayList;

import vuhid_tools.shared.Patient;





import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;

import com.google.gwt.user.client.ui.CustomScrollPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

import com.google.gwt.user.client.ui.AbsolutePanel;

import com.google.gwt.user.client.ui.Image;

import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Vuhid_tools implements EntryPoint {


	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final VUHIDServiceAsync VUHIDService = GWT
			.create(VUHIDService.class);
	
	
	
	private TextBox firstNameBox;
	private TextBox vuhididBox;
	private TextBox heightBox;
	private TextBox middleNameBox;
	private TextBox weightBox;
	private TextBox lastNameBox;
	private TextBox empiidBox;
	private TextBox emailBox;
	private TextBox addressBox;
	private TextBox bedidBox;
	private TextBox phoneNumberBox;
	private TextBox cityBox;
	private TextBox bloodTypeBox;
	private TextBox dobBox;
	private TextBox stateBox;
	private TextBox hospitalidBox;
	private TextBox zipBox;
	private TextBox genderBox;
	private Label statusLabel;
	
	private CellTable<Patient> resultsTable;
	
	
	private TextBox cFirstNameBox;
	private TextBox cLastNameBox;
	private TextBox cPhoneNumberBox;
	private TextBox cAddressBox;
	private TextBox deceasedBox;
	private confirmDialogBox cdb;
	private Label firstNameError;
	private Label empiidError;
	private Label phoneNumberError;
	private Label middleNameError;
	private Label bedidError;
	private Label dobError;
	private Label lastNameError;
	private Label bloodTypeError;
	private Label cFirstNameError;
	private Label addressError;
	private Label hospitalIDError;
	private Label cLastNameError;
	private Label cityError;
	private Label genderError;
	private Label cPhoneError;
	private Label stateError;
	private Label heightError;
	private Label cAddressError;
	private Label zipError;
	private Label weightError;
	private Label vuhididError;
	private Label emailError;
	private Label deceasedError;
	
	private Patient currentPatient;
	private ArrayList<Patient> currentSearchResults;
	
	
	private Patient selectedPatient1;
	private Patient selectedPatient2;

	

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		cdb = new confirmDialogBox();
		RootPanel rootPanel = RootPanel.get("main");
		rootPanel.setWidth("650px");
		rootPanel.setStyleName((String) null);
		
		AbsolutePanel absolutePanel = new AbsolutePanel();
		absolutePanel.setStyleName("outerPanel");
		rootPanel.add(absolutePanel, 0, 0);
		absolutePanel.setSize("590px", "1006px");
		
		AbsolutePanel patientInfoPanel = new AbsolutePanel();
		patientInfoPanel.setStyleName("patientInfoPanel");
		absolutePanel.add(patientInfoPanel, 0, 156);
		patientInfoPanel.setSize("408px", "572px");
		
		AbsolutePanel absolutePanel_2 = new AbsolutePanel();
		absolutePanel_2.setStyleName("patientInfoBar");
		patientInfoPanel.add(absolutePanel_2, 0, 0);
		absolutePanel_2.setSize("408px", "35px");
		
		Label lblNewLabel_1 = new Label("Patient Information");
		lblNewLabel_1.setStyleName("patientInfoTitle");
		absolutePanel_2.add(lblNewLabel_1, 24, 10);
		
		Button clear = new Button("New button");
		clear.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				clearAllBoxes();				
			}
		});
		clear.setText("Clear");
		patientInfoPanel.add(clear, 35, 523);
		
		Button search = new Button("New button");
		search.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				currentPatient = getCurrentPatient();
				
				invokeSearch(currentPatient, "Patient Search successful");
			}
		});
		search.setText("Search");
		patientInfoPanel.add(search, 100, 523);
		
		
		
		
		
		
		Button update = new Button("New button");
		update.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				Patient current = getCurrentPatient();
				
				if(current != null)
				{
					VUHIDService.PatientRegistryRecordRevised(current,
							new AsyncCallback<Boolean>() {
								public void onFailure(Throwable caught) {
									
									statusLabel.setText("Patient update request Failed with: " + caught.getMessage());	
								}
	
	
								@Override
								public void onSuccess(Boolean result) {
									if(result)
									{
										statusLabel.setText("Patient update request succeeded");														

									}
									else
									{
										statusLabel.setText("Patient update request failed");														

									}
														
								}
							
							});				
				
				}
				
				
			}
		});
		update.setText("Update");
		patientInfoPanel.add(update, 173, 523);
		
		Button createNew = new Button("New button");
		createNew.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Patient current = getCurrentPatient();
				
				if(current != null)
				{
					VUHIDService.PatientRegistryRecordAdded(current,
							new AsyncCallback<Boolean>() {
								public void onFailure(Throwable caught) {
									
									statusLabel.setText("New patient request Failed with: " + caught.getMessage());	
								}
	
	
								@Override
								public void onSuccess(Boolean result) {
									
									if(result)
									{
										statusLabel.setText("New patient request succeeded");														

									}
									else
									{
										statusLabel.setText("New patient request failed");	
									}
																							
								}
							
							});				
				
				}
				
				
				
			}
		});
		createNew.setText("Create New");
		patientInfoPanel.add(createNew, 248, 523);
		
		Label lblNewLabel_4 = new Label("First Name");
		lblNewLabel_4.setStyleName("patientInfoLabels");
		patientInfoPanel.add(lblNewLabel_4, 22, 56);
		lblNewLabel_4.setSize("74px", "15px");
		
		firstNameBox = new TextBox();
		patientInfoPanel.add(firstNameBox, 22, 73);
		firstNameBox.setSize("97px", "8px");
		
		firstNameError = new Label("");
		firstNameError.setStyleName("patientInfoErrorLabel");
		patientInfoPanel.add(firstNameError, 47, 93);
		firstNameError.setSize("58px", "12px");
		
		
		middleNameBox = new TextBox();
		patientInfoPanel.add(middleNameBox, 22, 127);
		middleNameBox.setSize("97px", "8px");
		
		lastNameError = new Label("");
		lastNameError.setStyleName("patientInfoErrorLabel");
		patientInfoPanel.add(lastNameError, 47, 202);
		lastNameError.setSize("58px", "12px");
		
		lastNameBox = new TextBox();
		patientInfoPanel.add(lastNameBox, 22, 182);
		lastNameBox.setSize("97px", "8px");
		
		Label lblLastName = new Label("Last Name");
		lblLastName.setStyleName("patientInfoLabels");
		patientInfoPanel.add(lblLastName, 22, 165);
		lblLastName.setSize("83px", "15px");
		
		addressError = new Label("");
		addressError.setStyleName("patientInfoErrorLabel");
		patientInfoPanel.add(addressError, 47, 256);
		addressError.setSize("58px", "12px");
		
		addressBox = new TextBox();
		patientInfoPanel.add(addressBox, 22, 236);
		addressBox.setSize("97px", "8px");
		
		cityBox = new TextBox();
		patientInfoPanel.add(cityBox, 22, 291);
		cityBox.setSize("97px", "8px");
		
		stateBox = new TextBox();
		patientInfoPanel.add(stateBox, 22, 346);
		stateBox.setSize("97px", "8px");
		
		zipBox = new TextBox();
		patientInfoPanel.add(zipBox, 22, 401);
		zipBox.setSize("97px", "8px");
		
		Label lblDateOfBirth = new Label("Address");
		lblDateOfBirth.setStyleName("patientInfoLabels");
		patientInfoPanel.add(lblDateOfBirth, 22, 219);
		lblDateOfBirth.setSize("74px", "15px");
		
		vuhididError = new Label("");
		vuhididError.setStyleName("patientInfoErrorLabel");
		patientInfoPanel.add(vuhididError, 47, 478);
		vuhididError.setSize("57px", "12px");
		
		vuhididBox = new TextBox();
		patientInfoPanel.add(vuhididBox, 22, 458);
		vuhididBox.setSize("97px", "8px");
		
		Label lblVuhidId = new Label("VUHID ID");
		lblVuhidId.setStyleName("patientInfoLabels");
		patientInfoPanel.add(lblVuhidId, 22, 441);
		lblVuhidId.setSize("77px", "15px");
		
		empiidError = new Label("");
		empiidError.setStyleName("patientInfoErrorLabel");
		patientInfoPanel.add(empiidError, 179, 93);
		empiidError.setSize("57px", "12px");
		
		empiidBox = new TextBox();
		patientInfoPanel.add(empiidBox, 154, 73);
		empiidBox.setSize("97px", "8px");
		
		Label lblEmail = new Label("EMPI ID");
		lblEmail.setStyleName("patientInfoLabels");
		patientInfoPanel.add(lblEmail, 154, 56);
		lblEmail.setSize("77px", "15px");
		
		cityError = new Label("");
		cityError.setStyleName("patientInfoErrorLabel");
		patientInfoPanel.add(cityError, 47, 311);
		cityError.setSize("57px", "12px");
		

		
		Label lblCity = new Label("City");
		lblCity.setStyleName("patientInfoLabels");
		patientInfoPanel.add(lblCity, 22, 274);
		lblCity.setSize("77px", "15px");
		
		stateError = new Label("");
		stateError.setStyleName("patientInfoErrorLabel");
		patientInfoPanel.add(stateError, 47, 366);
		stateError.setSize("57px", "12px");
		
		
		Label lblState = new Label("State");
		lblState.setStyleName("patientInfoLabels");
		patientInfoPanel.add(lblState, 22, 329);
		lblState.setSize("77px", "15px");
		
		zipError = new Label("");
		zipError.setStyleName("patientInfoErrorLabel");
		patientInfoPanel.add(zipError, 47, 421);
		zipError.setSize("57px", "12px");
		

		
		Label lblZip = new Label("Zip");
		lblZip.setStyleName("patientInfoLabels");
		patientInfoPanel.add(lblZip, 22, 384);
		lblZip.setSize("77px", "15px");
		
		bedidError = new Label("");
		bedidError.setStyleName("patientInfoErrorLabel");
		patientInfoPanel.add(bedidError, 179, 148);
		bedidError.setSize("57px", "12px");
		
		bedidBox = new TextBox();
		patientInfoPanel.add(bedidBox, 154, 128);
		bedidBox.setSize("97px", "8px");
		
		Label lblBedDi = new Label("Bed ID");
		lblBedDi.setStyleName("patientInfoLabels");
		patientInfoPanel.add(lblBedDi, 154, 111);
		lblBedDi.setSize("77px", "15px");
		
		bloodTypeError = new Label("");
		bloodTypeError.setStyleName("patientInfoErrorLabel");
		patientInfoPanel.add(bloodTypeError, 179, 203);
		bloodTypeError.setSize("57px", "12px");
		
		bloodTypeBox = new TextBox();
		patientInfoPanel.add(bloodTypeBox, 154, 183);
		bloodTypeBox.setSize("97px", "8px");
		
		Label lblBloodType = new Label("Blood Type");
		lblBloodType.setStyleName("patientInfoLabels");
		patientInfoPanel.add(lblBloodType, 154, 166);
		lblBloodType.setSize("77px", "15px");
		
		hospitalIDError = new Label("");
		hospitalIDError.setStyleName("patientInfoErrorLabel");
		patientInfoPanel.add(hospitalIDError, 179, 258);
		hospitalIDError.setSize("57px", "12px");
		
		hospitalidBox = new TextBox();
		patientInfoPanel.add(hospitalidBox, 154, 238);
		hospitalidBox.setSize("97px", "8px");
		
		Label lblDateOfBirth_1 = new Label("Hospital ID");
		lblDateOfBirth_1.setStyleName("patientInfoLabels");
		patientInfoPanel.add(lblDateOfBirth_1, 154, 221);
		lblDateOfBirth_1.setSize("77px", "15px");
		
		middleNameError = new Label("");
		middleNameError.setStyleName("patientInfoErrorLabel");
		patientInfoPanel.add(middleNameError, 47, 147);
		middleNameError.setSize("58px", "12px");

		
		Label lblMiddleName = new Label("Middle Name");
		lblMiddleName.setStyleName("patientInfoLabels");
		patientInfoPanel.add(lblMiddleName, 22, 110);
		lblMiddleName.setSize("107px", "15px");
		
		Label lblGender = new Label("Gender");
		lblGender.setStyleName("patientInfoLabels");
		patientInfoPanel.add(lblGender, 154, 275);
		lblGender.setSize("77px", "15px");
		
		genderBox = new TextBox();
		patientInfoPanel.add(genderBox, 154, 292);
		genderBox.setSize("97px", "8px");
		
		genderError = new Label("");
		genderError.setStyleName("patientInfoErrorLabel");
		patientInfoPanel.add(genderError, 179, 312);
		genderError.setSize("57px", "12px");
		
		heightError = new Label("");
		heightError.setStyleName("patientInfoErrorLabel");
		patientInfoPanel.add(heightError, 179, 366);
		heightError.setSize("57px", "12px");
		
		heightBox = new TextBox();
		patientInfoPanel.add(heightBox, 154, 346);
		heightBox.setSize("97px", "8px");
		
		Label lblHeight = new Label("Height");
		lblHeight.setStyleName("patientInfoLabels");
		patientInfoPanel.add(lblHeight, 154, 329);
		lblHeight.setSize("77px", "15px");
		
		Label lblWeight = new Label("Weight");
		lblWeight.setStyleName("patientInfoLabels");
		patientInfoPanel.add(lblWeight, 154, 384);
		lblWeight.setSize("77px", "15px");
		
		weightBox = new TextBox();
		patientInfoPanel.add(weightBox, 154, 401);
		weightBox.setSize("97px", "8px");
		
		emailBox = new TextBox();
		patientInfoPanel.add(emailBox, 154, 455);
		emailBox.setSize("97px", "8px");
		
		phoneNumberBox = new TextBox();
		patientInfoPanel.add(phoneNumberBox, 278, 73);
		phoneNumberBox.setSize("97px", "8px");
		
		weightError = new Label("");
		weightError.setStyleName("patientInfoErrorLabel");
		patientInfoPanel.add(weightError, 179, 421);
		weightError.setSize("57px", "12px");
		
		Label lblDateOfBirth_2 = new Label("Date of Birth");
		lblDateOfBirth_2.setStyleName("patientInfoLabels");
		patientInfoPanel.add(lblDateOfBirth_2, 278, 111);
		lblDateOfBirth_2.setSize("145px", "15px");
		
		dobBox = new TextBox();
		patientInfoPanel.add(dobBox, 278, 128);
		dobBox.setSize("97px", "8px");
		
		dobError = new Label("");
		dobError.setStyleName("patientInfoErrorLabel");
		patientInfoPanel.add(dobError, 303, 148);
		dobError.setSize("57px", "12px");
		
		Label lblEmail_1 = new Label("Email");
		lblEmail_1.setStyleName("patientInfoLabels");
		patientInfoPanel.add(lblEmail_1, 154, 438);
		lblEmail_1.setSize("77px", "15px");
		

		
		emailError = new Label("");
		emailError.setStyleName("patientInfoErrorLabel");
		patientInfoPanel.add(emailError, 179, 475);
		emailError.setSize("57px", "12px");
		
		Label lblPhoneNumber = new Label("Phone Number");
		lblPhoneNumber.setStyleName("patientInfoLabels");
		patientInfoPanel.add(lblPhoneNumber, 278, 56);
		lblPhoneNumber.setSize("107px", "15px");
		
	
		
		phoneNumberError = new Label("");
		phoneNumberError.setStyleName("patientInfoErrorLabel");
		patientInfoPanel.add(phoneNumberError, 303, 93);
		phoneNumberError.setSize("57px", "12px");
		
		Label lblContactFirstName = new Label("Contact First Name");
		lblContactFirstName.setStyleName("patientInfoLabels");
		patientInfoPanel.add(lblContactFirstName, 278, 165);
		lblContactFirstName.setSize("145px", "15px");
		
		cFirstNameBox = new TextBox();
		patientInfoPanel.add(cFirstNameBox, 278, 182);
		cFirstNameBox.setSize("97px", "8px");
		
		cFirstNameError = new Label("");
		cFirstNameError.setStyleName("patientInfoErrorLabel");
		patientInfoPanel.add(cFirstNameError, 303, 202);
		cFirstNameError.setSize("57px", "12px");
		
		Label lblContactLastName = new Label("Contact Last Name");
		lblContactLastName.setStyleName("patientInfoLabels");
		patientInfoPanel.add(lblContactLastName, 278, 219);
		lblContactLastName.setSize("145px", "15px");
		
		cLastNameBox = new TextBox();
		patientInfoPanel.add(cLastNameBox, 278, 236);
		cLastNameBox.setSize("97px", "8px");
		
		cLastNameError = new Label("");
		cLastNameError.setStyleName("patientInfoErrorLabel");
		patientInfoPanel.add(cLastNameError, 303, 256);
		cLastNameError.setSize("57px", "12px");
		
		Label lblContactPhoneNumber = new Label("Contact Phone No.");
		lblContactPhoneNumber.setStyleName("patientInfoLabels");
		patientInfoPanel.add(lblContactPhoneNumber, 278, 274);
		lblContactPhoneNumber.setSize("145px", "15px");
		
		cPhoneNumberBox = new TextBox();
		patientInfoPanel.add(cPhoneNumberBox, 278, 291);
		cPhoneNumberBox.setSize("97px", "8px");
		
		cPhoneError = new Label("");
		cPhoneError.setStyleName("patientInfoErrorLabel");
		patientInfoPanel.add(cPhoneError, 303, 311);
		cPhoneError.setSize("57px", "12px");
		
		Label lblContactAddress = new Label("Contact Address");
		lblContactAddress.setStyleName("patientInfoLabels");
		patientInfoPanel.add(lblContactAddress, 278, 329);
		lblContactAddress.setSize("107px", "15px");
		
		cAddressBox = new TextBox();
		patientInfoPanel.add(cAddressBox, 278, 346);
		cAddressBox.setSize("97px", "8px");
		
		cAddressError = new Label("");
		cAddressError.setStyleName("patientInfoErrorLabel");
		patientInfoPanel.add(cAddressError, 303, 366);
		cAddressError.setSize("57px", "12px");
		
		Label lblDeceased = new Label("Deceased");
		lblDeceased.setStyleName("patientInfoLabels");
		patientInfoPanel.add(lblDeceased, 278, 384);
		lblDeceased.setSize("107px", "15px");
		
		deceasedBox = new TextBox();
		patientInfoPanel.add(deceasedBox, 278, 401);
		deceasedBox.setSize("97px", "8px");
		
		deceasedError = new Label("");
		deceasedError.setStyleName("patientInfoErrorLabel");
		patientInfoPanel.add(deceasedError, 303, 421);
		deceasedError.setSize("57px", "12px");
		
		AbsolutePanel mainControlsPanel = new AbsolutePanel();
		mainControlsPanel.setStyleName("mainControlsPanel");
		absolutePanel.add(mainControlsPanel, 407, 156);
		mainControlsPanel.setSize("182px", "572px");
		
		AbsolutePanel absolutePanel_3 = new AbsolutePanel();
		absolutePanel_3.setStyleName("actionsBar");
		mainControlsPanel.add(absolutePanel_3, 0, 0);
		absolutePanel_3.setSize("182px", "35px");
		
		Label lblNewLabel_2 = new Label("Actions");
		lblNewLabel_2.setStyleName("actionsTitle");
		absolutePanel_3.add(lblNewLabel_2, 20, 10);
		
		Button getNewOVID = new Button("New button");
		getNewOVID.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				try {
					VUHIDService.getNewOVID(
							new AsyncCallback<String>() {
								public void onFailure(Throwable caught) {
									statusLabel.setText("New OVID request failed: " + caught.getMessage());	
								}

								public void onSuccess(String result) {
									statusLabel.setText("Request returned new OVID: " + result);	
									
									updateTextBox(vuhididBox, result);
																	
								}
							});
				} catch (Exception e) {
					statusLabel.setText(e.getMessage());	

				}
			}
		});
		
		getNewOVID.setText("Get new OVID");
		mainControlsPanel.add(getNewOVID, 25, 68);
		getNewOVID.setSize("136px", "28px");
		
		Button getNewPVID = new Button("New button");
		getNewPVID.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				cdb.initBox("Please enter a privacy class ", "getpvid", vuhididBox, VUHIDService, statusLabel, "Privacy Class: ");
				cdb.show();				
				
			}
		});
		getNewPVID.setText("Get new PVID");
		mainControlsPanel.add(getNewPVID, 25, 117);
		getNewPVID.setSize("136px", "28px");
		
		Button retire = new Button("New button");
		retire.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cdb.initBox("Are you sure you want to retire this VUHID ID?", "retire", vuhididBox, VUHIDService, statusLabel, "Reason: ");
				cdb.show();
				
			}
		});
		
		
		retire.setText("Retire");
		mainControlsPanel.add(retire, 25, 168);
		retire.setSize("136px", "28px");
		
		Button terminate = new Button("New button");
		terminate.setText("Terminate");
		mainControlsPanel.add(terminate, 25, 218);
		terminate.setSize("136px", "28px");
		terminate.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cdb.initBox("Are you sure you want to terminate this VUHID ID?", "terminate", vuhididBox, VUHIDService, statusLabel, "Reason: ");
				cdb.show();
				
			}
		});
		
		
		
		
		Button getReplacement = new Button("New button");
		getReplacement.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cdb.initBox("Are you sure you want to request a replacement for this VUHID ID?", "replacement", vuhididBox, VUHIDService, statusLabel, "Reason: ");
				cdb.show();
				
			}
		});
		getReplacement.setText("Get Replacement");
		mainControlsPanel.add(getReplacement, 25, 262);
		getReplacement.setSize("136px", "28px");
		
		Button dataLocations = new Button("New button");
		dataLocations.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				try {
					VUHIDService.getDataLocations(vuhididBox.getText(),
							
							new AsyncCallback<String[]>() {
								public void onFailure(Throwable caught) {
									
									statusLabel.setText("Get Data Locations request failed: " + caught.getMessage());	
								}

								public void onSuccess(String result[]) {
									String locations = "";
									
									for(String s: result)
									{
										locations = locations + s + " ";
									}
									
									statusLabel.setText("Get Data Locations request returned with locations: " + locations);	
																	
								}
							});
				} catch (Exception e) {
					statusLabel.setText(e.getMessage());	

				}
			}
		});
		dataLocations.setText("Data Locations");
		mainControlsPanel.add(dataLocations, 25, 311);
		dataLocations.setSize("136px", "28px");
		
		Label lblNewLabel_6 = new Label("Status: ");
		mainControlsPanel.add(lblNewLabel_6, 10, 401);
		
		statusLabel = new Label("");
		statusLabel.setStyleName("statusLabel");
		mainControlsPanel.add(statusLabel, 64, 401);
		statusLabel.setSize("97px", "184px");
		
		Button button = new Button("New button");
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				LoggerDialogBox ldb = new LoggerDialogBox(VUHIDService);
				ldb.center();
				ldb.show();
				
			}
		});
		button.setText("Save Report");
		mainControlsPanel.add(button, 25, 360);
		button.setSize("136px", "28px");
		
		Image image = new Image("image003.jpg");
		absolutePanel.add(image, 190, 0);
		image.setSize("241px", "100px");
		
		AbsolutePanel absolutePanel_1 = new AbsolutePanel();
		absolutePanel_1.setStyleName("topBar");
		absolutePanel.add(absolutePanel_1, 0, 116);
		absolutePanel_1.setSize("590px", "40px");
		
		Label lblNewLabel = new Label("VUHID Portal");
		lblNewLabel.setStyleName("vuhidPortalTitle");
		absolutePanel_1.add(lblNewLabel, 253, 10);
		lblNewLabel.setSize("131px", "31px");
		
		AbsolutePanel absolutePanel_5 = new AbsolutePanel();
		absolutePanel_5.setStyleName("searchPanel");
		absolutePanel.add(absolutePanel_5, 0, 730);
		absolutePanel_5.setSize("591px", "278px");
		
		final Button mergeButton = new Button("New button");
		mergeButton.setEnabled(false);
		mergeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				mergeButton.setEnabled(false);

				
				VUHIDService.PatientRegistryDuplicatesResolved(selectedPatient1, selectedPatient2, 
						new AsyncCallback<Boolean>() {
							public void onFailure(Throwable caught) {
								
								//statusLabel.setText("Patient merge request failed: " + caught.getMessage());	
						          Window.alert("Patient merge request failed: " + caught.getMessage());

							}

							public void onSuccess(Boolean result) {
								//statusLabel.setText("Patient merge new OVID: " + result);
								
								if(result)
								{
							          Window.alert("Patient merge request succeeded");						          
							          
							          clearAllBoxes();
							          clearErrorLabels();
							          
							          invokeSearch(currentPatient, "Requested merge and refreshed search results");
							          
								}
								else
								{
							          Window.alert("Patient merge request method failed");						          

								}

							}
						});
	
			}
		});

		
		
		absolutePanel_5.add(mergeButton, 40, 237);
		mergeButton.setText("Merge Duplicates");
		
		CustomScrollPanel scrollPanel = new CustomScrollPanel();
		scrollPanel.setStyleName("resultsTable");
		absolutePanel_5.add(scrollPanel, 40, 40);
		scrollPanel.setSize("517px", "180px");
		scrollPanel.removeHorizontalScrollbar();
		
		resultsTable = new CellTable<Patient>();
		resultsTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		TextColumn<Patient> firstNameCol = new TextColumn<Patient>() {
		      @Override
		      public String getValue(Patient p) {
		        return p.FirstName;
		      }
		    };
		    resultsTable.addColumn(firstNameCol, "First Name");
		    
		TextColumn<Patient> lastNameCol = new TextColumn<Patient>() {
		      @Override
		      public String getValue(Patient p) {
		        return p.LastName;
		      }
		    };
		    resultsTable.addColumn(lastNameCol, "Last Name");
		    
		TextColumn<Patient> dobCol = new TextColumn<Patient>() {
		      @Override
		      public String getValue(Patient p) {
		        return p.DOB;
		      }
		    };
		    resultsTable.addColumn(dobCol, "Date of Birth");		
		    
		TextColumn<Patient> vuhididCol = new TextColumn<Patient>() {
		      @Override
		      public String getValue(Patient p) {
		        return p.VUHID_ID;
		      }
		    };
		    resultsTable.addColumn(vuhididCol, "VUHID ID");		
			    		    
		    
		    scrollPanel.add(resultsTable);
		    resultsTable.setSize("517px", "180px");
		    
		    
		    
		    final MultiSelectionModel<Patient> selectionModel = new MultiSelectionModel<Patient>();
		    resultsTable.setSelectionModel(selectionModel);
		    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
		      public void onSelectionChange(SelectionChangeEvent event) {
		    	  
		    	  
		    	 ArrayList<Patient> selectedPatients= new ArrayList<Patient>(selectionModel.getSelectedSet());
		    	  	    	 
		    	 if(selectedPatients.size() == 1)
		    	 {
		    		 mergeButton.setEnabled(false);
		    		 selectedPatient1 = null;
		    		 selectedPatient2 = null;
		    		 
		    		 displayPatient(selectedPatients.get(0));
		    		 		    	
		    	 }
		    	 else if( selectedPatients.size() == 2)
		    	 {
		    		 mergeButton.setEnabled(true);
		    		 selectedPatient1 = selectedPatients.get(0);
		    		 selectedPatient2 = selectedPatients.get(1);
		    		 
		    		 //Union of patient display logic goes here 
		    		 
		    	 }
		    	 else
		    	 {
		    		 mergeButton.setEnabled(false);		
		    		 selectedPatient1 = null;
		    		 selectedPatient2 = null;
		    		 
		    	 }
		   		    	 
		        }
		      });

		    clearResultsTable();   
		

		AbsolutePanel absolutePanel_4 = new AbsolutePanel();
		absolutePanel_5.add(absolutePanel_4, 0, -8);
		absolutePanel_4.setStyleName("searchBar");
		absolutePanel_4.setSize("591px", "32px");
		
		Label lblNewLabel_3 = new Label("Search Results");
		lblNewLabel_3.setStyleName("searchbarTitle");
		absolutePanel_4.add(lblNewLabel_3, 24, 10);
		lblNewLabel_3.setSize("116px", "16px");
		
		Button btnNewButton_1 = new Button("New button");
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				clearResultsTable();


			}
		});
		btnNewButton_1.setText("Clear Table");
		absolutePanel_5.add(btnNewButton_1, 174, 237);
		btnNewButton_1.setSize("117px", "28px");


	}
	
	
	private void clearAllBoxes()
	{
		firstNameBox.setText("");
		vuhididBox.setText("");
		heightBox.setText("");
		middleNameBox.setText("");
		weightBox.setText("");
		lastNameBox.setText("");
		empiidBox.setText("");
		emailBox.setText("");
		addressBox.setText("");
		bedidBox.setText("");
		phoneNumberBox.setText("");
		cityBox.setText("");
		bloodTypeBox.setText("");
		 dobBox.setText("");
		stateBox.setText("");
		hospitalidBox.setText("");
		zipBox.setText("");
		genderBox.setText("");		
		
		cFirstNameBox.setText("");
		cLastNameBox.setText("");
		cPhoneNumberBox.setText("");
		cAddressBox.setText("");
		deceasedBox.setText("");	
		
		
		
		
		firstNameBox.setStyleName("gwt-TextBox");
		vuhididBox.setStyleName("gwt-TextBox");
		heightBox.setStyleName("gwt-TextBox");
		middleNameBox.setStyleName("gwt-TextBox");
		weightBox.setStyleName("gwt-TextBox");
		lastNameBox.setStyleName("gwt-TextBox");
		empiidBox.setStyleName("gwt-TextBox");
		emailBox.setStyleName("gwt-TextBox");
		addressBox.setStyleName("gwt-TextBox");
		bedidBox.setStyleName("gwt-TextBox");
		phoneNumberBox.setStyleName("gwt-TextBox");
		cityBox.setStyleName("gwt-TextBox");
		bloodTypeBox.setStyleName("gwt-TextBox");
		 dobBox.setStyleName("gwt-TextBox");
		stateBox.setStyleName("gwt-TextBox");
		hospitalidBox.setStyleName("gwt-TextBox");
		zipBox.setStyleName("gwt-TextBox");
		genderBox.setStyleName("gwt-TextBox");	
		
		cFirstNameBox.setStyleName("gwt-TextBox");
		cLastNameBox.setStyleName("gwt-TextBox");
		cPhoneNumberBox.setStyleName("gwt-TextBox");
		cAddressBox.setStyleName("gwt-TextBox");
		deceasedBox.setStyleName("gwt-TextBox");	
		
		
	}
	
	
	
	private void clearResultsTable()
	{
		
		resultsTable.setRowCount(0, true);
		resultsTable.setRowData(0, new ArrayList<Patient>());
	
	}
	
	
	static void updateTextBox(TextBox targetBox, String message)
	{
		
		String currentContent;
		
		currentContent = targetBox.getText();
		
		if(((!currentContent.isEmpty()) || (!message.isEmpty())) && (!currentContent.equals(message)))
		{
			targetBox.setStyleName("changedTextBox");
		}
		else
		{
			targetBox.setStyleName("gwt-TextBox");

		}
		targetBox.setText(message);
	}
	
	
	
	private Patient getCurrentPatient()
	{
		
		clearErrorLabels();
		boolean vaildData = true;
		
		/*
		if(firstNameBox.getText().equals(""))
		{
			firstNameError.setText("Invalid");
			vaildData = false;
		}
		if(vuhididBox.getText().equals(""))
		{
			vuhididError.setText("Invalid");
			vaildData = false;
		}
		
		/*
		if(middleNameBox.getText().equals(""))
		{
			middleNameError.setText("Invalid");
			vaildData = false;
		}
		*/
		
		/*
		if(lastNameBox.getText().equals(""))
		{
			lastNameError.setText("Invalid");
			vaildData = false;
		}
		if(empiidBox.getText().equals(""))
		{
			empiidError.setText("Invalid");
			vaildData = false;
		}
		if(emailBox.getText().equals(""))
		{
			emailError.setText("Invalid");
			vaildData = false;
		}
		if(addressBox.getText().equals(""))
		{
			addressError.setText("Invalid");
			vaildData = false;
		}
		if(bedidBox.getText().equals(""))
		{
			bedidError.setText("Invalid");
			vaildData = false;
		}
		if(phoneNumberBox.getText().equals(""))
		{
			phoneNumberError.setText("Invalid");
			vaildData = false;
		}
		if(cityBox.getText().equals(""))
		{
			cityError.setText("Invalid");
			vaildData = false;
		}
		if(bloodTypeBox.getText().equals(""))
		{
			bloodTypeError.setText("Invalid");
			vaildData = false;
		}
		if(dobBox.getText().equals(""))
		{
			dobError.setText("Invalid");
			vaildData = false;
		}
		if(stateBox.getText().equals(""))
		{
			stateError.setText("Invalid");
			vaildData = false;
		}
		/*
		if(hospitalidBox.getText().equals(""))
		{
			hospitalIDError.setText("Invalid");
			vaildData = false;
		}
		*/
		/*
		if(zipBox.getText().equals(""))
		{
			zipError.setText("Invalid");
			vaildData = false;
		}
		if(genderBox.getText().equals(""))
		{
			genderError.setText("Invalid");
			vaildData = false;
		}
		if(cFirstNameBox.getText().equals(""))
		{
			cFirstNameError.setText("Invalid");
			vaildData = false;
		}
		if(cLastNameBox.getText().equals(""))
		{
			cLastNameError.setText("Invalid");
			vaildData = false;
		}
		if(cPhoneNumberBox.getText().equals(""))
		{
			cPhoneError.setText("Invalid");
			vaildData = false;
		}
		if(cAddressBox.getText().equals(""))
		{
			cAddressError.setText("Invalid");
			vaildData = false;
		}
		if(deceasedBox.getText().equals(""))
		{
			deceasedError.setText("Invalid");
			vaildData = false;
		}
		
		*/
		
		
		
		boolean deceased;
		
		if(deceasedBox.getText().toLowerCase().startsWith("t"))
			deceased = true;
		else 
			deceased = false;
		
		int height = -1;
		int weight = -1;
		
		if(!heightBox.getText().equals(""))
		{
			try
			{
				 height = Integer.parseInt(heightBox.getText());
			}
			catch(NumberFormatException ne)
			{
				heightError.setText("Invalid");
				vaildData = false;
				
				
			}
		
		}
		
		
		
		if(!weightBox.getText().equals(""))
		{
			try
			{
				 weight = Integer.parseInt(weightBox.getText());
			}
			catch(NumberFormatException ne)
			{
				weightError.setText("Invalid");
				vaildData = false;
							
			}		
		}
		
		
		
		if(vaildData)
		{
			return new Patient(firstNameBox.getText(), lastNameBox.getText(), vuhididBox.getText(), dobBox.getText(), genderBox.getText(), emailBox.getText(), 
				phoneNumberBox.getText(), addressBox.getText(), cityBox.getText(), stateBox.getText(), zipBox.getText(), empiidBox.getText(), bloodTypeBox.getText(), 
				bedidBox.getText(), cFirstNameBox.getText(), cLastNameBox.getText(), cPhoneNumberBox.getText(), cAddressBox.getText(), weight, 
				height, deceased);
		}
		
		return null;
	}
	
	
	
	
	private void clearErrorLabels()
	{
		firstNameError.setText("");
		empiidError.setText("");
		phoneNumberError.setText("");
		middleNameError.setText("");
		bedidError.setText("");
		dobError.setText("");
		lastNameError.setText("");
		bloodTypeError.setText("");
		cFirstNameError.setText("");
		addressError.setText("");
		hospitalIDError.setText("");
		cLastNameError.setText("");
		cityError.setText("");
		genderError.setText("");
		cPhoneError.setText("");
		stateError.setText("");
		heightError.setText("");
		cAddressError.setText("");
		zipError.setText("");
		weightError.setText("");
		vuhididError.setText("");
		emailError.setText("");
		deceasedError.setText("");

	}
	
	
	public void displayPatient(Patient p)
	{
		updateTextBox(firstNameBox, p.FirstName);
		updateTextBox(vuhididBox, p.VUHID_ID);
		updateTextBox(heightBox, p.heightInches+"");
		updateTextBox(middleNameBox, "N/A");
		updateTextBox(weightBox, p.weightLbs+"");
		updateTextBox(lastNameBox, p.LastName);
		updateTextBox(empiidBox, p.EMPIId);
		updateTextBox(emailBox, p.email);
		updateTextBox(addressBox, p.address);
		updateTextBox(bedidBox, p.bedID);
		updateTextBox(phoneNumberBox, p.phoneNumber);
		updateTextBox(cityBox, p.city);
		updateTextBox(bloodTypeBox, p.bloodType);
		updateTextBox(dobBox, p.DOB);
		updateTextBox(stateBox, p.state);
		updateTextBox(hospitalidBox, "N/A");
		updateTextBox(zipBox, p.zip);
		updateTextBox(genderBox, p.gender);
		updateTextBox(cFirstNameBox, p.contactFirstName);
		updateTextBox(cLastNameBox, p.contactLastName);
		updateTextBox(cPhoneNumberBox, p.contactPhoneNumber);
		updateTextBox(cAddressBox, p.contactAddress);
		updateTextBox(deceasedBox, p.deceased+"");
	}
	
	
	
	
	
	
	
	
	public void invokeSearch(Patient targetPatient, final String SuccessMessage )
	{
		
		if(targetPatient != null)
		{
			VUHIDService.PatientRegistryFindCandidatesQuery(targetPatient,
					new AsyncCallback<ArrayList<Patient>>() {
						public void onFailure(Throwable caught) {
							
							statusLabel.setText("Patient Search Failed with: " + caught.getMessage());	
						}


						@Override
						public void onSuccess(ArrayList<Patient> result) {
							
							currentSearchResults = result;
																					
							statusLabel.setText(SuccessMessage);	
							
							resultsTable.setRowCount(currentSearchResults.size(), true);
							resultsTable.setRowData(0, currentSearchResults);
							
															
						}
						
					});				
		
		}
		
	}
	
	
	
	
}
