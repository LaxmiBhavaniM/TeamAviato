/**
 * Autogenerated by Thrift Compiler (0.9.3)
 * <p>
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *
 * @generated
 */
package com.sciencegateway.dataingestor.aurora.client.sdk;


/**
 * Defines the policy for launching a new cron job when one is already running.
 */
public enum CronCollisionPolicy implements org.apache.thrift.TEnum {
    /**
     * Kills the existing job with the colliding name, and runs the new cron job.
     */
    KILL_EXISTING(0),
    /**
     * Cancels execution of the new job, leaving the running job in tact.
     */
    CANCEL_NEW(1),
    /**
     * DEPRECATED. For existing jobs, treated the same as CANCEL_NEW.
     * createJob will reject jobs with this policy.
     */
    RUN_OVERLAP(2);

    private final int value;

    private CronCollisionPolicy(int value) {
        this.value = value;
    }

    /**
     * Find a the enum type by its integer value, as defined in the Thrift IDL.
     *
     * @return null if the value is not found.
     */
    public static CronCollisionPolicy findByValue(int value) {
        switch (value) {
            case 0:
                return KILL_EXISTING;
            case 1:
                return CANCEL_NEW;
            case 2:
                return RUN_OVERLAP;
            default:
                return null;
        }
    }

    /**
     * Get the integer value of this enum value, as defined in the Thrift IDL.
     */
    public int getValue() {
        return value;
    }
}
