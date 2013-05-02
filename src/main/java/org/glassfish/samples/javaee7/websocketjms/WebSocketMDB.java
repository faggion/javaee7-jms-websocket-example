/**
 * Copyright © 2013, 2013, Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.glassfish.samples.javaee7.websocketjms;

import java.util.logging.Level;
import javax.ejb.MessageDriven;
import javax.inject.Named;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.logging.Logger;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.jms.JMSException;

/**
 * This MDB will fire CDI events with the JMS payload, classified as
 * <code>@WSJMSMessage</code>
 *
 * @author Bruno Borges <bruno.borges at oracle.com>
 */
@Named
@MessageDriven(mappedName = "jms/myQueue")
public class WebSocketMDB implements MessageListener {

    @Inject
    @WSJMSMessage
    Event<Message> jmsEvent;

    @Override
    public void onMessage(Message msg) {
        try {
            Logger.getLogger(WebSocketMDB.class.getName()).log(Level.INFO, "Message received [id={0}] [payload={1}]", new Object[]{msg.getJMSMessageID(), msg.getBody(String.class)});
            jmsEvent.fire(msg);
        } catch (JMSException ex) {
            Logger.getLogger(WebSocketMDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
