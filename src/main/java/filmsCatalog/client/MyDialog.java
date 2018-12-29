package filmsCatalog.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;


public class MyDialog extends DialogBox {
    public MyDialog() {

        setText("New film:");
        TextBox busNumber = new TextBox();


        setAnimationEnabled(true);
        setGlassEnabled(true);

        Button ok = new Button("OK");
        ok.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                MyDialog.this.hide();
            }
        });
        Label label = new Label("Add information about your bus.");
        VerticalPanel panel = new VerticalPanel();
        panel.setHeight("100");
        panel.setWidth("300");
        panel.setSpacing(10);

        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        panel.add(label);
        panel.add(ok);
        setWidget(panel);
    }
}
