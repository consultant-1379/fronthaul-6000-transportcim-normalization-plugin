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

package com.ericsson.oss.mediation.tcim.normalization.plugin.fronthaul6000.datatypes;

/**
 * Managed for FRONTHAUL-6000 nodeType.
 */
public enum ManagedFronthaul6000NeTypeEnum {
    FRONTHAUL6000("FRONTHAUL-6000");

    private String value;

    private ManagedFronthaul6000NeTypeEnum(String value) {
        this.value = value;
    }

    public static boolean contains(String neType) {

        for (ManagedFronthaul6000NeTypeEnum netypeManaged : ManagedFronthaul6000NeTypeEnum.values()) {
            if (netypeManaged.value.equals(neType)) {
                return true;
            }
        }
        return false;
    }

}
