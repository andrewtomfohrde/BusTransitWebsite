package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * WebSocket session handler for the Visual Transit Simulator.
 *
 * <p>This class manages client connections, receives simulation commands,
 * executes them, and sends simulation updates back to the client.
 */
@ServerEndpoint(
    value = "/simulator",
    subprotocols = {"web_server"}
)
public class WebServerSession {

  private Session session;
  private WebServerSessionState webServerState;

  /**
   * Constructs a new WebServerSession.
   */
  public WebServerSession() {
    System.out.println("class loaded " + this.getClass());
  }

  /**
   * Runs when a client connects to the WebSocket.
   *
   * @param session the WebSocket session
   */
  @OnOpen
  public void onOpen(Session session) {
    System.out.println("session opened");
    try {
      this.session = session;
      webServerState = new WebServerSessionState();

      VisualTransitSimulator simulator =
          new VisualTransitSimulator(
              URLDecoder.decode(
                  getClass()
                      .getClassLoader()
                      .getResource("config.txt")
                      .getFile(),
                  "UTF-8"),
              this);

      webServerState.getCommands().put("getRoutes", new GetRoutesCommand(simulator));
      webServerState.getCommands().put("getVehicles", new GetVehiclesCommand(simulator));
      webServerState.getCommands().put("start", new StartCommand(simulator));
      webServerState.getCommands().put("update", new UpdateCommand(simulator));
      webServerState.getCommands().put("initLines", new InitLinesCommand(simulator));
      webServerState.getCommands().put("pause", new PauseCommand(simulator));
      webServerState.getCommands().put("registerVehicle",
          new RegisterVehicleCommand(simulator));
      webServerState.getCommands().put("lineIssue", new LineIssueCommand(simulator));

    } catch (UnsupportedEncodingException uee) {
      uee.printStackTrace();
    }
  }

  /**
   * Executes a simulation command received from the client.
   *
   * @param message JSON-formatted command string
   */
  @OnMessage
  public void onMessage(String message) {
    JsonObject commandJson = JsonParser.parseString(message).getAsJsonObject();
    String command = commandJson.get("command").getAsString();
    if (command != null && webServerState.getCommands().containsKey(command)) {
      SimulatorCommand simulatorCommand =
          webServerState.getCommands().get(command);
      simulatorCommand.execute(this, commandJson);
    }
  }

  /**
   * Sends JSON data to the client.
   *
   * @param message JSON object to send
   */
  public void sendJson(JsonObject message) {
    try {
      session.getBasicRemote().sendText(message.toString());
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Handles WebSocket errors.
   *
   * @param e the error that occurred
   */
  @OnError
  public void onError(Throwable e) {
    e.printStackTrace();
  }

  /**
   * Runs when the WebSocket session is closed.
   *
   * @param session the WebSocket session
   */
  @OnClose
  public void onClose(Session session) {
    System.out.println("session closed");
    this.session = null;
  }
}
