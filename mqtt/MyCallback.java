package mqtt;


public class MyCallback implements MqttCallback {

    @Override
    public void messageArrived(String topic, MqttMessage msg) throws Exception {
        System.out.println("topic: " + topic);
        System.out.println("message: " + new String(msg.getPayload()));
    }

    @Override
    public void connectionLost(MqttDisconnectResponse response) {
        System.out.println("connection lost: " + response.getReasonString());
    }

    @Override
    public void deliveryComplete(org.eclipse.paho.mqttv5.client.IMqttToken token) {
        System.out.println("delivery complete");
    }

    @Override
    public void disconnected(MqttDisconnectResponse response) {}

    @Override
    public void mqttErrorOccurred(MqttException e) {}

    @Override
    public void authPacketArrived(int i, MqttProperties mp) {}
}