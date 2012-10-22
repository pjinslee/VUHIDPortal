package vuhid_tools.client;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;

public class LoggerDialogBox extends DialogBox

{
	private TextBox reasonBox;
	private Button continueButton;
	private Button cancelButton;
	private AbsolutePanel absolutePanel;
	private Label messageArea;
	private Label boxLabel;
	
	//private VUHIDServiceAsync VUHIDService;

	public LoggerDialogBox(final VUHIDServiceAsync VUHIDService) {
		
		absolutePanel = new AbsolutePanel();
		absolutePanel.setStyleName("vuhidDialogBox");
		setWidget(absolutePanel);
		absolutePanel.setSize("262px", "145px");


		Image image = new Image("vuhid_logo_small.jpg");
		absolutePanel.add(image, 10, 0);
		image.setSize("116px", "39px");
		
		final TextBox monthBox = new TextBox();
		absolutePanel.add(monthBox, 88, 56);
		monthBox.setSize("28px", "7px");
		
		Label lblNewLabel = new Label("Month:");
		absolutePanel.add(lblNewLabel, 36, 56);
		
		Label lblYear = new Label("Year:");
		absolutePanel.add(lblYear, 132, 56);
		lblYear.setSize("38px", "19px");
		
		final TextBox yearBox = new TextBox();
		absolutePanel.add(yearBox, 170, 56);
		yearBox.setSize("46px", "7px");
		
		
		//this.VUHIDService = VUHIDService;
		
		Button btnNewButton = new Button("New button");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				
				
				int month = 01;
				int year = 2012;
				
				
				if(!monthBox.getText().equals(""))
				{
					try
					{
						month = Integer.parseInt(monthBox.getText());
					}
					catch(NumberFormatException ne)
					{						
						
					}
				
				}
				
				
				
				if(!yearBox.getText().equals(""))
				{
					try
					{
						 year = Integer.parseInt(yearBox.getText());
					}
					catch(NumberFormatException ne)
					{
									
					}		
				}
				
				
				
				
				
				
				VUHIDService.report(month, year, new AsyncCallback<String>() {
					
					public void onFailure(Throwable caught) {
						
						//statusLabel.setText("Get Data Locations request failed: " + caught.getMessage());
						Window.alert("Save report failed: " + caught.getMessage());
					}

					@Override
					public void onSuccess(String result) {
						
						//Window.alert(result);

						//Append things to string to make URL
						//open url in browser or download it
						UrlBuilder urlBuilder = new UrlBuilder();
					    urlBuilder.setHost(Window.Location.getHost());
					    urlBuilder.setPath(result);

					    String port = Window.Location.getPort();
					    if (!port.isEmpty())
					        urlBuilder.setPort(Integer.parseInt(port));

					    Window.open(urlBuilder.buildString(), "_blank", "enabled");
						
					}
				});


				hide();
			}
		});
		btnNewButton.setText("Save Report");
		absolutePanel.add(btnNewButton, 36, 97);
		btnNewButton.setSize("90px", "28px");
		
		Button btnNewButton_1 = new Button("New button");
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		btnNewButton_1.setText("Cancel");
		absolutePanel.add(btnNewButton_1, 152, 97);
		btnNewButton_1.setSize("90px", "28px");
		
		
	}
}