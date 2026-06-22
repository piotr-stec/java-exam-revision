package mqtt;


public class Subscriber {
    public static void main(String[] args) {
        IMqttClient cli;

        try {
            cli = new MqttClient("tcp://192.168.1.1", "sub1");
            cli.connect();

            cli.setCallback(new MyCallback());
            cli.subscribe("dom/salon/temp", 1);

            cli.disconnect();
        } catch (MqttException e) {
            System.out.println("mqtt: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}