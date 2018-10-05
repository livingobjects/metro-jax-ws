/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.message;

import java.io.ByteArrayInputStream;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.message.saaj.SAAJMessage;
import com.sun.xml.ws.api.addressing.AddressingVersion;
import com.sun.xml.ws.api.addressing.WSEndpointReference;

import junit.framework.TestCase;

public class GenerateToElementTest extends TestCase {


  public static final String TEST_NS = "http://jaxws.dev.java.net/";
  private HeaderList testInstance;

  public GenerateToElementTest(String name) {
      super(name);
  }

  @Override
  protected void setUp() throws Exception {
      super.setUp();

      testInstance = new HeaderList(SOAPVersion.SOAP_11);
  }

  @Override
  protected void tearDown() throws Exception {
      super.tearDown();

      testInstance = null;
  }

  public void testCorrectToFromResMsg() throws Exception {
      String reqMsgStr =
          "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:a=\"http://www.w3.org/2005/08/addressing\">" +
              "<s:Header>" +
                  "<a:Action s:mustUnderstand=\"1\">http://example.org/action/echoIn</a:Action>" +
                  "<a:ReplyTo s:actor=\"http://www.w3.org/2003/05/soap-envelope/role/none\"><a:Address>http://www.microsoft.com/</a:Address></a:ReplyTo>" +
                  "<a:MessageID>urn:uuid:d715800d-67e2-4254-a86e-e31a1bfaecab</a:MessageID>" +
                  "<a:ReplyTo><a:Address>http://10.244.13.245:8000/2eaddcdf-e10b-41c5-9f1f-a7ac50853fc3/4b1de783-1499-4419-9634-465f8797a0a8</a:Address></a:ReplyTo>" +
                  "<a:To s:mustUnderstand=\"1\">http://scl58353.us.oracle.com:9902/WSAddressingCR_Service_WCF/WSAddressing10.svc/Echo4</a:To>" +
              "</s:Header>" +
              "<s:Body>" +
                  "<echoIn xmlns=\"http://example.org/echo\">test1151</echoIn>" +
              "</s:Body>" +
          "</s:Envelope>";

      String respMsgStr =
          "<?xml version='1.0' encoding='UTF-8'?>" +
          "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
              "<S:Body>" +
                  "<ns0:echoOut xmlns:ns0=\"http://example.org/echo\">test1151</ns0:echoOut>" +
              "</S:Body>" +
          "</S:Envelope>";

      String finalToFromResponseMsg = "";
      WSEndpointReference originalReplyToFromRequestMsg = null;

      AddressingVersion av = AddressingVersion.W3C;
      SOAPVersion sv = SOAPVersion.SOAP_11;
      String action = "http://example.org/action/echoOut";

      SAAJMessage reqMsg = new SAAJMessage(makeSOAPMessage(reqMsgStr));
      SAAJMessage respMsg = new SAAJMessage(makeSOAPMessage(respMsgStr));
      HeaderList requestHdrs = (HeaderList) reqMsg.getHeaders();
      originalReplyToFromRequestMsg = requestHdrs.getReplyTo(av, sv);

      Packet responsePacket=null;
      try{
          responsePacket = new Packet(reqMsg).createServerResponse(respMsg, av, sv, action);
      } catch (Exception e) {
          e.printStackTrace();
      }

      //check toHeader
      finalToFromResponseMsg = AddressingUtils.getTo(responsePacket.getHeaderList(), av, sv);
      assertEquals(finalToFromResponseMsg, originalReplyToFromRequestMsg.getAddress());
  }
  private SOAPMessage makeSOAPMessage(String msg) throws Exception {
      MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
      SOAPMessage message = factory.createMessage();
      Source src = new StreamSource(new ByteArrayInputStream(msg.getBytes()));
      message.getSOAPPart().setContent(src);
      return message;
  }
}
