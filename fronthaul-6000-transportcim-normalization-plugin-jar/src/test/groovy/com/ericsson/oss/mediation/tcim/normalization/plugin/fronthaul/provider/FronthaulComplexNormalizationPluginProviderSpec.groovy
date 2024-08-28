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

import com.ericsson.cds.cdi.support.spock.CdiSpecification
import com.ericsson.oss.itpf.datalayer.dps.persistence.ManagedObject
import com.ericsson.oss.itpf.datalayer.dps.stub.RuntimeConfigurableDps
import com.ericsson.oss.mediation.tcim.normalization.api.data.NormalizedObject
import com.ericsson.oss.mediation.tcim.normalization.api.data.TransportCimNormalizationContext
import com.ericsson.oss.mediation.tcim.normalization.api.data.TransportCimNormalizationProperties

class FronthaulComplexNormalizationPluginProviderSpec extends CdiSpecification {

    protected RuntimeConfigurableDps runtimeDps

    def setup() {
        runtimeDps = cdiInjectorRule.getService(RuntimeConfigurableDps.class)
    }

    /**
     * Initializing context.
     * @param nodeAddress
     * @param neType
     * @param complexAttributes
     * @param targetNormalizedMap
     */
    def initContext(nodeAddress, neType, complexAttributes, targetNormalizedMap) {
        def properties = setupProperties(nodeAddress, neType)
        def ctx = new TransportCimNormalizationContext(properties)
        ctx.setTargetNormalizedObjectMap(targetNormalizedMap)
        ctx.setComplexAttributes(complexAttributes)
        return ctx
    }

    /**
     * setting properties.
     * @param nodeAddress
     * @param neType
     */
    def setupProperties(nodeAddress, neType) {
        def properties = new TransportCimNormalizationProperties()
        properties.put("neType", neType)
        properties.put("nodeFdn", "Network=1,Node=" + nodeAddress)
        properties.put("platformType", 'FRONTHAUL-6000')
        properties.put("moRootNormalization", 'MeContext=CORE82MLTN01,ManagedElement=1')
        properties.put("isComplexNormalizationPluginDefined", true)

        return properties
    }

    /**
     * Create complex attributes with MOs and without MO attributes.
     * @param nodeAddress
     */
    def getComplextAttrWithUNRMSMOsWithOutAttr(nodeAddress) {
        List<Object> complexAttributes = new ArrayList<Object>()
        complexAttributes.add(getInterfaceMO(nodeAddress, 'Un1.Pt4'))
        complexAttributes.add(getInterfaceMO(nodeAddress, 'Un2.Pt8'))
        complexAttributes.add(getInterfaceMO(nodeAddress, 'Un3.Pt6'))
        complexAttributes.add(getInterfaceMO(nodeAddress, 'Un4.Pt9'))
        complexAttributes.add(getInterfaceMO(nodeAddress, 'Un5.Pt9'))
        return complexAttributes
    }


    /**
     * Creating Interface MO.
     * @param nodeAddress
     */
    def getInterfaceMO(nodeAddress, name) {
        def interfaceFdn = "MeContext=" + nodeAddress + ",ManagedElement=" + nodeAddress + ",Transport=1,Interfaces=1,Interface=" + name
        def interfaceAttributes = new HashMap<String, Object>()
        ManagedObject interfaceUNRMMO = runtimeDps.addManagedObject().withFdn(interfaceFdn).type("Interface").namespace("MINI-LINK_NODE_MODEL").version('1.0.0')
                .addAttributes(interfaceAttributes).generateTree().build()
        return interfaceUNRMMO
    }

    /**
     * creating target normalized object map.
     * @param nodeAddress
     */
    def setupTargetNormalizedMap(nodeAddress) {
        Map<String, NormalizedObject> targetNormMap = new HashMap<String, NormalizedObject>()
        def interfaceFdn = "Network=1,Node=" + nodeAddress +",Interfaces=1,Interface=Un1.Pt4"
        NormalizedObject interfaceNormMO = new NormalizedObject(interfaceFdn, 'Un1.Pt4', 'OSS_TCIM', 'Interface')
        def interfaceMirrorFdn = "MeContext=" + nodeAddress + ",ManagedElement=" + nodeAddress + ",Transport=1,Interfaces=1,Interface=Un1.Pt4"
        interfaceNormMO.setAttribute("speed", 10000);
        interfaceNormMO.setMirrorMoFdn(interfaceMirrorFdn);
        targetNormMap.put(interfaceFdn, interfaceNormMO)

        def interfaceFdn2 = "Network=1,Node=" + nodeAddress +",Interfaces=1,Interface=Un2.Pt8"
        NormalizedObject interfaceNrmMO2 = new NormalizedObject(interfaceFdn2, 'Un2.Pt8', 'OSS_TCIM', 'Interface')
        def interfaceMirrorFdn2 = "MeContext=" + nodeAddress + ",ManagedElement=" + nodeAddress + ",Transport=1,Interfaces=1,Interface=Un2.Pt8"
        interfaceNrmMO2.setAttribute("speed", 2488300);
        interfaceNrmMO2.setMirrorMoFdn(interfaceMirrorFdn2);
        targetNormMap.put(interfaceFdn2, interfaceNrmMO2)

        def interfaceFdn3 = "Network=1,Node=" + nodeAddress +",Interfaces=1,Interface=Un3.Pt6"
        NormalizedObject interfaceNormMO3 = new NormalizedObject(interfaceFdn3, 'Un3.Pt6', 'OSS_TCIM', 'Interface')
        def interfaceMirrorFdn3 = "MeContext=" + nodeAddress + ",ManagedElement=" + nodeAddress + ",Transport=1,Interfaces=1,Interface=Un3.Pt6"
        interfaceNormMO3.setAttribute("speed", 10312500);
        interfaceNormMO3.setMirrorMoFdn(interfaceMirrorFdn3);
        targetNormMap.put(interfaceFdn3, interfaceNormMO3)

        def interfaceFdn4 = "Network=1,Node=" + nodeAddress +",Interfaces=1,Interface=Un4.Pt9"
        NormalizedObject interfaceNrmMO4 = new NormalizedObject(interfaceFdn4, 'Un4.Pt9', 'OSS_TCIM', 'Interface')
        def interfaceMirrorFdn4 = "MeContext=" + nodeAddress + ",ManagedElement=" + nodeAddress + ",Transport=1,Interfaces=1,Interface=Un4.Pt9"
        interfaceNrmMO4.setAttribute("speed", 25781250);
        interfaceNrmMO4.setMirrorMoFdn(interfaceMirrorFdn4);
        targetNormMap.put(interfaceFdn4, interfaceNrmMO4)

        def interfaceFdn5 = "Network=1,Node=" + nodeAddress +",Interfaces=1,Interface=Un5.Pt9"
        NormalizedObject interfaceNrmMO5 = new NormalizedObject(interfaceFdn5, 'Un5.Pt9', 'OSS_TCIM', 'Interface')
        def interfaceMirrorFdn5 = "MeContext=" + nodeAddress + ",ManagedElement=" + nodeAddress + ",Transport=1,Interfaces=1,Interface=Un5.Pt9"
        interfaceNrmMO5.setAttribute("speed", 25781260);
        interfaceNrmMO5.setMirrorMoFdn(interfaceMirrorFdn5);
        targetNormMap.put(interfaceFdn5, interfaceNrmMO5)

        return targetNormMap
    }
}
