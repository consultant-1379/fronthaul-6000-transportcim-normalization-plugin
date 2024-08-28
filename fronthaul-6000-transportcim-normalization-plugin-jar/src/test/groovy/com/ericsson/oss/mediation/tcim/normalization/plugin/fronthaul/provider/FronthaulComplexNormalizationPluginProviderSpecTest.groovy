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
package com.ericsson.oss.mediation.tcim.normalization.plugin.fronthaul.provider

import com.ericsson.cds.cdi.support.rule.ObjectUnderTest
import com.ericsson.oss.mediation.tcim.normalization.api.data.NormalizedObject
import com.ericsson.oss.mediation.tcim.normalization.plugin.fronthaul6000.provider.FRONTHAUL6000ComplexNormalizationPluginProvider

import spock.lang.Unroll

/**
 * Test specification for Fronthaul complex normalization plugin
 */
class FronthaulComplexNormalizationPluginProviderSpecTest extends FronthaulComplexNormalizationPluginProviderSpec{


    @ObjectUnderTest
    FRONTHAUL6000ComplexNormalizationPluginProvider fronthaul6000ComplexNormalizationPluginProvider

    /**
     * Testing Fronthaul-6000 complex normalization plugin and verifying the normalized MOs by passing MOs list
     */
    @Unroll
    def "TestSuite 1: verify the right normalization execution on a network without Mos"() {

        given: "createMos"
        def complexAttributes = new ArrayList<Object>()

        and: "create Input Context"
        def targetNormalizedMap = new HashMap<String, NormalizedObject>()
        def ctx = initContext(nodeAddress, neType, complexAttributes, targetNormalizedMap)

        when: "execute onEvent method"
        println("Provider name is "+fronthaul6000ComplexNormalizationPluginProvider.getName())
        fronthaul6000ComplexNormalizationPluginProvider.execute(ctx)

        then: "assert Context Result"
        //Validating all the Normalized Interface MO's
        NormalizedObject interfaceNormMO = ctx.getTargetNormalizedObjectMap().get("Network=1,Node=" + nodeAddress + ",Interfaces=1,Interface=1")
        assert interfaceNormMO==null

        //Validating all the Normalized RadioLinkTerminal MO's
        NormalizedObject radioLinkTerminalNormMO = ctx.getTargetNormalizedObjectMap().get("Network=1,Node=" + nodeAddress +",Interfaces=1,Interface=1,RadioLinkTerminal=1")
        assert radioLinkTerminalNormMO==null

        //Validating all the Normalized CarrierTermination MO's
        NormalizedObject carrierTermNormMO1 = ctx.getTargetNormalizedObjectMap().get("Network=1,Node=" + nodeAddress +",Interfaces=1,Interface=1,CarrierTermination=1")
        assert carrierTermNormMO1==null

        //Validating all the Normalized MimoGroup MO's
        NormalizedObject mimoGroupNormMo = ctx.getTargetNormalizedObjectMap().get("Network=1,Node=" + nodeAddress +",MimoGroups=1,MimoGroup=1")
        assert mimoGroupNormMo==null

        //Validating all the Normalized XPICPair MO's
        NormalizedObject xpicPairNormMo = ctx.getTargetNormalizedObjectMap().get("Network=1,Node=" + nodeAddress +",XpicPairs=1,XpicPair=1")
        assert xpicPairNormMo==null

        //Validating all the Normalized RadioLinkProtectionGroup MO's
        NormalizedObject radioLinkProtGrpNormMO = ctx.getTargetNormalizedObjectMap().get("Network=1,Node=" + nodeAddress +",RadioLinkProtectionGroups=1,RadioLinkProtectionGroup=1")
        assert radioLinkProtGrpNormMO==null

        where: "used parameters for Mos and Eventcontext"
        nodeAddress         | neType
        'FH6050_001'        | 'FRONTHAUL-6000'
        'FH6020_001'        | 'Fronthaul-6020'   // negative case.. for coverage
    }

    /**
     * Testing Fronthaul-6000 complex normalization plugin and verifying the normalized MOs by passing UNRM MOs and without attributes
     */
    @Unroll
    def "TestSuite 3: verify the right normalization execution on a network"() {

        given: "createMos"
        def complexAttributes = getComplextAttrWithUNRMSMOsWithOutAttr(nodeAddress)

        and: "create Input Context"
        def targetNormalizedMap = setupTargetNormalizedMap(nodeAddress)
        def ctx = initContext(nodeAddress, neType, complexAttributes, targetNormalizedMap)

        when: "execute onEvent method"
        fronthaul6000ComplexNormalizationPluginProvider.execute(ctx)

        then: "assert Context Result"
        //Validating all the Normalized Interface MO's
        NormalizedObject interfaceNormMO = ctx.getTargetNormalizedObjectMap().get("Network=1,Node=" + nodeAddress + ",Interfaces=1,Interface=Un1.Pt4")
        final String layerRate = (String)interfaceNormMO.getAttribute('layer-rate');
        assert layerRate=='OPTICAL_TRANSPORT_1Gb'

        NormalizedObject interfaceNormMO2 = ctx.getTargetNormalizedObjectMap().get("Network=1,Node=" + nodeAddress + ",Interfaces=1,Interface=Un2.Pt8")
        final String layerRate2 = (String)interfaceNormMO2.getAttribute('layer-rate');
        assert layerRate2=='OPTICAL_TRANSPORT_2_5Gb'

        NormalizedObject interfaceNormMO3 = ctx.getTargetNormalizedObjectMap().get("Network=1,Node=" + nodeAddress + ",Interfaces=1,Interface=Un3.Pt6")
        final String layerRate3 = (String)interfaceNormMO3.getAttribute('layer-rate');
        assert layerRate3=='OPTICAL_TRANSPORT_10Gb'

        NormalizedObject interfaceNormMO4 = ctx.getTargetNormalizedObjectMap().get("Network=1,Node=" + nodeAddress + ",Interfaces=1,Interface=Un4.Pt9")
        final String layerRate4 = (String)interfaceNormMO4.getAttribute('layer-rate');
        assert layerRate4=='OPTICAL_TRANSPORT_25Gb'

        NormalizedObject interfaceNormMO5 = ctx.getTargetNormalizedObjectMap().get("Network=1,Node=" + nodeAddress + ",Interfaces=1,Interface=Un5.Pt9")
        final String layerRate5 = (String)interfaceNormMO5.getAttribute('layer-rate');
        assert layerRate5=='UNDEFINED'

        where: "used parameters for Mos and Eventcontext"
        nodeAddress         | neType
        'FH6050_001'        | 'FRONTHAUL-6000'
    }
}
