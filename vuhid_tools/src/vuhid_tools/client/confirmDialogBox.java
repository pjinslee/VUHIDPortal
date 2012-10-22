package vuhid_tools.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;

public class confirmDialogBox extends DialogBox

{
	private TextBox reasonBox;
	private Button continueButton;
	private Button cancelButton;
	private AbsolutePanel absolutePanel;
	private Label messageArea;
	private Label boxLabel;
	
	/*
	private String message;
	private String action;
	private TextBox vuhididBox;
	private VUHIDServiceAsync VUHIDService;
	private Label statusLabel;

	*/
	
	
	public confirmDialogBox() {
		
		absolutePanel = new AbsolutePanel();
		absolutePanel.setStyleName("vuhidDialogBox");
		setWidget(absolutePanel);
		absolutePanel.setSize("363px", "197px");
		
		messageArea = new Label("Sample Message");
		absolutePanel.add(messageArea, 32, 45);
		messageArea.setSize("295px", "39px");
		
		boxLabel = new Label("Reason:");
		absolutePanel.add(boxLabel, 24, 100);
		boxLabel.setSize("58px", "25px");
		
		reasonBox = new TextBox();
		absolutePanel.add(reasonBox, 88, 97);
		reasonBox.setSize("240px", "16px");
		

		
		Image image = new Image("vuhid_logo_small.jpg");
		absolutePanel.add(image, 10, 0);
		image.setSize("116px", "39px");
	}
	
	
	public void initBox(String message, String action, final TextBox vuhididBox,  final VUHIDServiceAsync VUHIDService, final Label statusLabel, String boxLabelString)
	{
		this.center();
		
		boxLabel.setText(boxLabelString);

			/*	
		this.message = message;
		this.action = action;
		this.vuhididBox = vuhididBox;
		this.VUHIDService = VUHIDService;
		this.statusLabel = statusLabel;
		*/
		reasonBox.setText("");
		messageArea.setText(message);
		
		if(action == "retire")
		{			
			continueButton = new Button("New button");
			continueButton.setText("Continue");
			absolutePanel.add(continueButton, 100, 142);
			continueButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					hide();

					try {
						VUHIDService.retireID(vuhididBox.getText(), reasonBox.getText(), 
						
								new AsyncCallback<Integer>() {
									public void onFailure(Throwable caught) {
										
										statusLabel.setText("Retire ID request failed: " + caught.getMessage());	
									}

									public void onSuccess(Integer result) {
										statusLabel.setText("Retire ID request returned with: " + result);	
										
										Vuhid_tools.updateTextBox(vuhididBox, "");
																		
									}


								});
					} 
					catch (Exception e) {
						statusLabel.setText(e.getMessage());	

						
					}
				}
			});
			
			
			cancelButton = new Button("New button");
			cancelButton.setText("Cancel");
			absolutePanel.add(cancelButton, 190, 142);
			cancelButton.setSize("67px", "28px");
			cancelButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					hide();
				}
			});	
		}
		else if(action == "terminate"){
			continueButton = new Button("New button");
			continueButton.setText("Continue");
			absolutePanel.add(continueButton, 100, 142);
			continueButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					
					hide();
					try {
						VUHIDService.terminateID(vuhididBox.getText(), reasonBox.getText(), 
						
								new AsyncCallback<Integer>() {
									public void onFailure(Throwable caught) {
										
										statusLabel.setText("Terminate ID request failed: " + caught.getMessage());	
									}

									public void onSuccess(Integer result) {
										statusLabel.setText("Terminate ID request returned with: " + result);	
										
										Vuhid_tools.updateTextBox(vuhididBox, "");
																		
									}


								});
					} catch (Exception e) {
						statusLabel.setText(e.getMessage());	

					}
				}
			});
			
			
			cancelButton = new Button("New button");
			cancelButton.setText("Cancel");
			absolutePanel.add(cancelButton, 190, 142);
			cancelButton.setSize("67px", "28px");
			cancelButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					hide();
				}
			});	
			
			
		}
		else if(action == "replacement")
		{
			continueButton = new Button("New button");
			continueButton.setText("Continue");
			absolutePanel.add(continueButton, 100, 142);
			continueButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					
					hide();
					try {
						VUHIDService.getReplacementID(vuhididBox.getText(), reasonBox.getText(), 
						
								new AsyncCallback<String>() {
									public void onFailure(Throwable caught) {
										
										statusLabel.setText("Get Replacement ID request failed: " + caught.getMessage());	
									}

									public void onSuccess(String result) {
										statusLabel.setText("Get Replacement ID request returned with: " + result);										
										Vuhid_tools.updateTextBox(vuhididBox, result);
																		
									}
								});
					} catch (Exception e) {
						statusLabel.setText(e.getMessage());	

					}
				}
			});
			
			
			cancelButton = new Button("New button");
			cancelButton.setText("Cancel");
			absolutePanel.add(cancelButton, 190, 142);
			cancelButton.setSize("67px", "28px");
			cancelButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					hide();
				}
			});	
			
		}
		else if(action == "getpvid")
		{
			continueButton = new Button("New button");
			continueButton.setText("Continue");
			absolutePanel.add(continueButton, 100, 142);
			continueButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					
					hide();
					try {
						VUHIDService.getNewPVID(reasonBox.getText(), 
						
								new AsyncCallback<String>() {
									public void onFailure(Throwable caught) {
										
										statusLabel.setText("Get New PVID request failed: " + caught.getMessage());	
									}

									public void onSuccess(String result) {
										statusLabel.setText("Get New PVID request returned with: " + result);										
										Vuhid_tools.updateTextBox(vuhididBox, result);
																		
									}
								});
					} catch (Exception e) {
						statusLabel.setText(e.getMessage());	

					}
				}
			});
			
			
			cancelButton = new Button("New button");
			cancelButton.setText("Cancel");
			absolutePanel.add(cancelButton, 190, 142);
			cancelButton.setSize("67px", "28px");
			cancelButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					hide();
				}
			});	
			
		}
		
		
		
		
		
		
		
		
	}
	


	
	
}
