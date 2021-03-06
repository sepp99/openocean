/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.openocean.internal.messages;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.smarthome.core.util.HexUtils;

/**
 *
 * @author Daniel Weber - Initial contribution
 */
public class RDVersionResponse extends Response {

    protected String appVersion = "";
    protected String apiVersion = "";
    protected String chipId = "";
    protected String description = "";

    public RDVersionResponse(Response response) {
        this(response.getPayload().length, 0, response.getPayload());
    }

    RDVersionResponse(int dataLength, int optionalDataLength, byte[] payload) {
        super(dataLength, optionalDataLength, payload);

        if (payload.length < 33) {
            return;
        }

        try {
            appVersion = String.format("%d.%d.%d.%d", payload[1] & 0xff, payload[2] & 0xff, payload[3] & 0xff,
                    payload[4] & 0xff);
            apiVersion = String.format("%d.%d.%d.%d", payload[5] & 0xff, payload[6] & 0xff, payload[7] & 0xff,
                    payload[8] & 0xff);

            byte[] chip = new byte[4];
            System.arraycopy(payload, 9, chip, 0, 4);
            chipId = HexUtils.bytesToHex(chip);

            StringBuffer sb = new StringBuffer();
            for (int i = 17; i < payload.length; i++) {
                sb.append((char) (payload[i] & 0xff));
            }
            description = sb.toString();
            _isValid = true;

        } catch (Exception e) {
            responseType = ResponseType.RET_ERROR;
        }
    }

    @NonNull
    public String getAPPVersion() {
        return appVersion;
    }

    @NonNull
    public String getAPIVersion() {
        return apiVersion;
    }

    @NonNull
    public String getChipID() {
        return chipId;
    }

    @NonNull
    public String getDescription() {
        return description;
    }
}
