package mqtt;

public class Publisher {
    public static void main(String[] args) {
        IMqttClient cli;

        try {
            cli = new MqttClient("tcp://192.168.1.1", "pub1");
            cli.connect();

            String payload = "temp msg";
            MqttMessage msg = new MqttMessage(payload.getBytes());
            msg.setQos(1);
            msg.setRetained(true);
            cli.publish("dom/salon/temp", msg);

            cli.disconnect();
            
        } catch (MqttException e) {
            System.out.println("mqtt: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}