/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.openocean.internal.eep.D2_01;

import static org.openhab.binding.openocean.OpenOceanBindingConstants.CHANNELTYPE_REPEATERMODE;

import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.eclipse.smarthome.core.types.State;
import org.openhab.binding.openocean.OpenOceanBindingConstants;
import org.openhab.binding.openocean.internal.messages.ERP1Message;
import org.openhab.binding.openocean.internal.messages.ERP1Message.RORG;

/**
 *
 * @author Daniel Weber - Initial contribution
 */
public class D2_01_0F_NodON extends D2_01 {

    public D2_01_0F_NodON() {
        super();
    }

    public D2_01_0F_NodON(ERP1Message packet) {
        super(packet);
    }

    @Override
    protected void convertFromCommandImpl(Command command, String channelId, State currentState, Configuration config) {

        if (channelId.equalsIgnoreCase(CHANNELTYPE_REPEATERMODE)) {

            if (command instanceof RefreshType) {
                senderId = null; // make this message invalid as we do not support refresh of repeter status
            } else if (command instanceof StringType) {
                switch (((StringType) command).toString()) {
                    case OpenOceanBindingConstants.REPEATERMODE_LEVEL_1:
                        setRORG(RORG.MSC).setData((byte) 0x00, (byte) 0x46, (byte) 0x08, (byte) 0x01, (byte) 0x01);
                        break;
                    case OpenOceanBindingConstants.REPEATERMODE_LEVEL_2:
                        setRORG(RORG.MSC).setData((byte) 0x00, (byte) 0x46, (byte) 0x08, (byte) 0x01, (byte) 0x02);
                        break;
                    default:
                        setRORG(RORG.MSC).setData((byte) 0x00, (byte) 0x46, (byte) 0x08, (byte) 0x00, (byte) 0x00);
                }
            }
        } else {
            super.convertFromCommandImpl(command, channelId, currentState, config);
        }
    }
}
