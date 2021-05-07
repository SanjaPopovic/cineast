package org.vitrivr.cineast.api.messages.session;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.vitrivr.cineast.api.messages.credentials.Credentials;
import org.vitrivr.cineast.api.messages.interfaces.Message;
import org.vitrivr.cineast.api.messages.interfaces.MessageType;

/**
 * Message to the requester to transfer the access credentials of the current session.
 *
 * @author lucaro
 * @version 1.0
 * @created 08.05.17
 */
public class StartSessionMessage implements Message {

  /**
   * {@link Credentials} of the session with the user.
   */
  private Credentials credentials;

  /**
   * Constructor for the StartSessionMessage object.
   *
   * @param credentials Credentials of the current session to be sent to the requester.
   */
  @JsonCreator
  public StartSessionMessage(@JsonProperty("credentials") Credentials credentials) {
    this.credentials = credentials;
  }

  /**
   * Getter for credentials.
   *
   * @return {@link Credentials}
   */
  public Credentials getCredentials() {
    return this.credentials;
  }

  /**
   * Returns the type of particular message. Expressed as MessageTypes enum.
   *
   * @return {@link MessageType}
   */
  @Override
  public MessageType getMessageType() {
    return MessageType.SESSION_START;
  }

  @Override
  public String toString() {
    return String.format("StartSessionMessage [credentials=%s]", credentials);
  }

}
