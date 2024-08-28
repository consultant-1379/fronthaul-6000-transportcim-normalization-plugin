/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2023
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/

package com.ericsson.oss.mediation.tcim.normalization.plugin.fronthaul6000.netypes;

import static com.ericsson.oss.mediation.tcim.normalization.plugin.fronthaul6000.constants.Fronthaul6000Constants.NORMALIZED_ATTRIBUTE_LAYER_RATE;
import static com.ericsson.oss.mediation.tcim.normalization.plugin.fronthaul6000.constants.Fronthaul6000Constants.NORMALIZED_ATTRIBUTE_SPEED;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.oss.itpf.datalayer.dps.persistence.ManagedObject;
import com.ericsson.oss.mediation.tcim.normalization.api.data.NormalizedObject;
import com.ericsson.oss.mediation.tcim.normalization.api.data.TransportCimNormalizationContext;
import com.ericsson.oss.mediation.tcim.normalization.plugin.fronthaul6000.datatypes.MirrorMoMaps;

/**
 * Fronthaul6000 NeType Management of mirrorMO
 */
public final class Fronthaul6000 {
    private static final Logger logger = LoggerFactory.getLogger(Fronthaul6000.class);

    public static void manageFronthaul6000(final TransportCimNormalizationContext ctx, final MirrorMoMaps mirrorMoMaps) {
        manageInterface(ctx, mirrorMoMaps);
    }

    private static void manageInterface(final TransportCimNormalizationContext ctx, final MirrorMoMaps mirrorMoMaps) {
        for (Map.Entry<String, ManagedObject> interfaceMoEntry : mirrorMoMaps.getInterfaceMap().entrySet()) {
            if (interfaceMoEntry.getValue() != null) {
                logger.debug("Interface mirror MO is {}", interfaceMoEntry);
                final NormalizedObject normalizedInterface = getAssociatedNormalizedMO(ctx, interfaceMoEntry.getValue().getFdn());
                logger.info("The normalizedInterface value is {} ", normalizedInterface);
                if (normalizedInterface != null && normalizedInterface.getAttribute(NORMALIZED_ATTRIBUTE_SPEED) != null
                        && !normalizedInterface.getAttribute(NORMALIZED_ATTRIBUTE_SPEED).toString().isEmpty()) {
                    final int speed = Integer.parseInt(normalizedInterface.getAttribute(NORMALIZED_ATTRIBUTE_SPEED).toString());
                    logger.info("The speed value is {} ", speed);
                    if (speed <= 1250000) {
                        normalizedInterface.setAttribute(NORMALIZED_ATTRIBUTE_LAYER_RATE, "OPTICAL_TRANSPORT_1Gb");
                        logger.info("OPTICAL_TRANSPORT_1Gb value has been set");
                    } else if (speed > 1250000 && speed <= 2488300) {
                        normalizedInterface.setAttribute(NORMALIZED_ATTRIBUTE_LAYER_RATE, "OPTICAL_TRANSPORT_2_5Gb");
                        logger.info("OPTICAL_TRANSPORT_2_5Gb value has been set");
                    } else if (speed > 2488300 && speed <= 10312500) {
                        normalizedInterface.setAttribute(NORMALIZED_ATTRIBUTE_LAYER_RATE, "OPTICAL_TRANSPORT_10Gb");
                        logger.info("OPTICAL_TRANSPORT_10Gb value has been set");
                    } else if (speed > 10312500 && speed <= 25781250) {
                        normalizedInterface.setAttribute(NORMALIZED_ATTRIBUTE_LAYER_RATE, "OPTICAL_TRANSPORT_25Gb");
                        logger.info("OPTICAL_TRANSPORT_25Gb value has been set");
                    } else {
                        normalizedInterface.setAttribute(NORMALIZED_ATTRIBUTE_LAYER_RATE, "UNDEFINED");
                        logger.info("UNDEFINED value has been set");
                    }
                }
            }
        }
    }

    private static NormalizedObject getAssociatedNormalizedMO(final TransportCimNormalizationContext ctx, final String mirrorFdn) {
        logger.info("mirrorFdn {}", mirrorFdn);
        for (final Map.Entry<String, NormalizedObject> normalizedObjectEntry : ctx.getTargetNormalizedObjectMap().entrySet()) {
            logger.info("Normalized Mo Mirror FDN: {}", normalizedObjectEntry.getValue().getMirrorMoFdn());
            if (normalizedObjectEntry.getValue().getMirrorMoFdn().equals(mirrorFdn)) {
                logger.info("Normalized Mo FDN: {}", normalizedObjectEntry.getValue().getFdn());
                return normalizedObjectEntry.getValue();
            }
        }
        logger.info("Entry not identified by mirror fdn {}", mirrorFdn);
        return null;
    }

}
