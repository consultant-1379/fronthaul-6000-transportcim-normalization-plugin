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

package com.ericsson.oss.mediation.tcim.normalization.plugin.fronthaul6000.provider;

import static com.ericsson.oss.mediation.tcim.normalization.api.data.TransportCimNormalizationProperties.NE_TYPE;
import static com.ericsson.oss.mediation.tcim.normalization.plugin.fronthaul6000.netypes.Fronthaul6000.manageFronthaul6000;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.oss.mediation.tcim.normalization.api.data.TransportCimNormalizationContext;
import com.ericsson.oss.mediation.tcim.normalization.api.exceptions.TransportCimException;
import com.ericsson.oss.mediation.tcim.normalization.plugin.ComplexNormalizationPluginProvider;
import com.ericsson.oss.mediation.tcim.normalization.plugin.fronthaul6000.datatypes.ManagedFronthaul6000NeTypeEnum;
import com.ericsson.oss.mediation.tcim.normalization.plugin.fronthaul6000.datatypes.MirrorMoMaps;

/**
 * Class implementing the Fronthaul6000 Normalization Plugin. Implements the interface {@link FRONTHAUL6000ComplexNormalizationPluginProvider}.
 */

public class FRONTHAUL6000ComplexNormalizationPluginProvider implements ComplexNormalizationPluginProvider {

    private static final Logger logger = LoggerFactory.getLogger(FRONTHAUL6000ComplexNormalizationPluginProvider.class);

    @Override
    public String getName() {
        return FRONTHAUL6000ComplexNormalizationPluginProvider.class.getName();
    }

    @Override
    public void execute(final TransportCimNormalizationContext ctx) throws TransportCimException {
        logger.info("Started execution of {} for node: {}", FRONTHAUL6000ComplexNormalizationPluginProvider.class.getName(), ctx.getNodeFdn());
        final String neType = ctx.getProperties().getProperty(NE_TYPE);
        logger.info("The neType got is {} ", neType);
        if (ManagedFronthaul6000NeTypeEnum.contains(neType)) {
            final MirrorMoMaps mirrorMoMaps = new MirrorMoMaps(ctx.getComplexAttributes());
            manageFronthaul6000(ctx, mirrorMoMaps);
        } else {
            logger.warn("Complex Plugin {} DOES NOT Manage {} neType", FRONTHAUL6000ComplexNormalizationPluginProvider.class.getName(), neType);
        }
    }
}
