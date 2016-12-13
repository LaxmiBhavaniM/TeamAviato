/**
 * Autogenerated by Thrift Compiler (0.9.3)
 * <p>
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *
 * @generated
 */
package com.sciencegateway.dataingestor.aurora.client.sdk;

import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;

import javax.annotation.Generated;
import java.util.*;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-12-08")
public class GetQuotaResult implements org.apache.thrift.TBase<GetQuotaResult, GetQuotaResult._Fields>, java.io.Serializable, Cloneable, Comparable<GetQuotaResult> {
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetQuotaResult");
    private static final org.apache.thrift.protocol.TField QUOTA_FIELD_DESC = new org.apache.thrift.protocol.TField("quota", org.apache.thrift.protocol.TType.STRUCT, (short) 1);
    private static final org.apache.thrift.protocol.TField PROD_SHARED_CONSUMPTION_FIELD_DESC = new org.apache.thrift.protocol.TField("prodSharedConsumption", org.apache.thrift.protocol.TType.STRUCT, (short) 2);
    private static final org.apache.thrift.protocol.TField NON_PROD_SHARED_CONSUMPTION_FIELD_DESC = new org.apache.thrift.protocol.TField("nonProdSharedConsumption", org.apache.thrift.protocol.TType.STRUCT, (short) 3);
    private static final org.apache.thrift.protocol.TField PROD_DEDICATED_CONSUMPTION_FIELD_DESC = new org.apache.thrift.protocol.TField("prodDedicatedConsumption", org.apache.thrift.protocol.TType.STRUCT, (short) 4);
    private static final org.apache.thrift.protocol.TField NON_PROD_DEDICATED_CONSUMPTION_FIELD_DESC = new org.apache.thrift.protocol.TField("nonProdDedicatedConsumption", org.apache.thrift.protocol.TType.STRUCT, (short) 5);
    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    // isset id assignments
    private static final _Fields optionals[] = {_Fields.PROD_SHARED_CONSUMPTION, _Fields.NON_PROD_SHARED_CONSUMPTION, _Fields.PROD_DEDICATED_CONSUMPTION, _Fields.NON_PROD_DEDICATED_CONSUMPTION};

    static {
        schemes.put(StandardScheme.class, new GetQuotaResultStandardSchemeFactory());
        schemes.put(TupleScheme.class, new GetQuotaResultTupleSchemeFactory());
    }

    static {
        Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
        tmpMap.put(_Fields.QUOTA, new org.apache.thrift.meta_data.FieldMetaData("quota", org.apache.thrift.TFieldRequirementType.DEFAULT,
                new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ResourceAggregate.class)));
        tmpMap.put(_Fields.PROD_SHARED_CONSUMPTION, new org.apache.thrift.meta_data.FieldMetaData("prodSharedConsumption", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ResourceAggregate.class)));
        tmpMap.put(_Fields.NON_PROD_SHARED_CONSUMPTION, new org.apache.thrift.meta_data.FieldMetaData("nonProdSharedConsumption", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ResourceAggregate.class)));
        tmpMap.put(_Fields.PROD_DEDICATED_CONSUMPTION, new org.apache.thrift.meta_data.FieldMetaData("prodDedicatedConsumption", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ResourceAggregate.class)));
        tmpMap.put(_Fields.NON_PROD_DEDICATED_CONSUMPTION, new org.apache.thrift.meta_data.FieldMetaData("nonProdDedicatedConsumption", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ResourceAggregate.class)));
        metaDataMap = Collections.unmodifiableMap(tmpMap);
        org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetQuotaResult.class, metaDataMap);
    }

    /**
     * Total allocated resource quota.
     */
    public ResourceAggregate quota; // required
    /**
     * Resources consumed by production jobs from a shared resource pool.
     */
    public ResourceAggregate prodSharedConsumption; // optional
    /**
     * Resources consumed by non-production jobs from a shared resource pool.
     */
    public ResourceAggregate nonProdSharedConsumption; // optional
    /**
     * Resources consumed by production jobs from a dedicated resource pool.
     */
    public ResourceAggregate prodDedicatedConsumption; // optional
    /**
     * Resources consumed by non-production jobs from a dedicated resource pool.
     */
    public ResourceAggregate nonProdDedicatedConsumption; // optional

    public GetQuotaResult() {
    }

    public GetQuotaResult(
            ResourceAggregate quota) {
        this();
        this.quota = quota;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public GetQuotaResult(GetQuotaResult other) {
        if (other.isSetQuota()) {
            this.quota = new ResourceAggregate(other.quota);
        }
        if (other.isSetProdSharedConsumption()) {
            this.prodSharedConsumption = new ResourceAggregate(other.prodSharedConsumption);
        }
        if (other.isSetNonProdSharedConsumption()) {
            this.nonProdSharedConsumption = new ResourceAggregate(other.nonProdSharedConsumption);
        }
        if (other.isSetProdDedicatedConsumption()) {
            this.prodDedicatedConsumption = new ResourceAggregate(other.prodDedicatedConsumption);
        }
        if (other.isSetNonProdDedicatedConsumption()) {
            this.nonProdDedicatedConsumption = new ResourceAggregate(other.nonProdDedicatedConsumption);
        }
    }

    public GetQuotaResult deepCopy() {
        return new GetQuotaResult(this);
    }

    @Override
    public void clear() {
        this.quota = null;
        this.prodSharedConsumption = null;
        this.nonProdSharedConsumption = null;
        this.prodDedicatedConsumption = null;
        this.nonProdDedicatedConsumption = null;
    }

    /**
     * Total allocated resource quota.
     */
    public ResourceAggregate getQuota() {
        return this.quota;
    }

    /**
     * Total allocated resource quota.
     */
    public GetQuotaResult setQuota(ResourceAggregate quota) {
        this.quota = quota;
        return this;
    }

    public void unsetQuota() {
        this.quota = null;
    }

    /**
     * Returns true if field quota is set (has been assigned a value) and false otherwise
     */
    public boolean isSetQuota() {
        return this.quota != null;
    }

    public void setQuotaIsSet(boolean value) {
        if (!value) {
            this.quota = null;
        }
    }

    /**
     * Resources consumed by production jobs from a shared resource pool.
     */
    public ResourceAggregate getProdSharedConsumption() {
        return this.prodSharedConsumption;
    }

    /**
     * Resources consumed by production jobs from a shared resource pool.
     */
    public GetQuotaResult setProdSharedConsumption(ResourceAggregate prodSharedConsumption) {
        this.prodSharedConsumption = prodSharedConsumption;
        return this;
    }

    public void unsetProdSharedConsumption() {
        this.prodSharedConsumption = null;
    }

    /**
     * Returns true if field prodSharedConsumption is set (has been assigned a value) and false otherwise
     */
    public boolean isSetProdSharedConsumption() {
        return this.prodSharedConsumption != null;
    }

    public void setProdSharedConsumptionIsSet(boolean value) {
        if (!value) {
            this.prodSharedConsumption = null;
        }
    }

    /**
     * Resources consumed by non-production jobs from a shared resource pool.
     */
    public ResourceAggregate getNonProdSharedConsumption() {
        return this.nonProdSharedConsumption;
    }

    /**
     * Resources consumed by non-production jobs from a shared resource pool.
     */
    public GetQuotaResult setNonProdSharedConsumption(ResourceAggregate nonProdSharedConsumption) {
        this.nonProdSharedConsumption = nonProdSharedConsumption;
        return this;
    }

    public void unsetNonProdSharedConsumption() {
        this.nonProdSharedConsumption = null;
    }

    /**
     * Returns true if field nonProdSharedConsumption is set (has been assigned a value) and false otherwise
     */
    public boolean isSetNonProdSharedConsumption() {
        return this.nonProdSharedConsumption != null;
    }

    public void setNonProdSharedConsumptionIsSet(boolean value) {
        if (!value) {
            this.nonProdSharedConsumption = null;
        }
    }

    /**
     * Resources consumed by production jobs from a dedicated resource pool.
     */
    public ResourceAggregate getProdDedicatedConsumption() {
        return this.prodDedicatedConsumption;
    }

    /**
     * Resources consumed by production jobs from a dedicated resource pool.
     */
    public GetQuotaResult setProdDedicatedConsumption(ResourceAggregate prodDedicatedConsumption) {
        this.prodDedicatedConsumption = prodDedicatedConsumption;
        return this;
    }

    public void unsetProdDedicatedConsumption() {
        this.prodDedicatedConsumption = null;
    }

    /**
     * Returns true if field prodDedicatedConsumption is set (has been assigned a value) and false otherwise
     */
    public boolean isSetProdDedicatedConsumption() {
        return this.prodDedicatedConsumption != null;
    }

    public void setProdDedicatedConsumptionIsSet(boolean value) {
        if (!value) {
            this.prodDedicatedConsumption = null;
        }
    }

    /**
     * Resources consumed by non-production jobs from a dedicated resource pool.
     */
    public ResourceAggregate getNonProdDedicatedConsumption() {
        return this.nonProdDedicatedConsumption;
    }

    /**
     * Resources consumed by non-production jobs from a dedicated resource pool.
     */
    public GetQuotaResult setNonProdDedicatedConsumption(ResourceAggregate nonProdDedicatedConsumption) {
        this.nonProdDedicatedConsumption = nonProdDedicatedConsumption;
        return this;
    }

    public void unsetNonProdDedicatedConsumption() {
        this.nonProdDedicatedConsumption = null;
    }

    /**
     * Returns true if field nonProdDedicatedConsumption is set (has been assigned a value) and false otherwise
     */
    public boolean isSetNonProdDedicatedConsumption() {
        return this.nonProdDedicatedConsumption != null;
    }

    public void setNonProdDedicatedConsumptionIsSet(boolean value) {
        if (!value) {
            this.nonProdDedicatedConsumption = null;
        }
    }

    public void setFieldValue(_Fields field, Object value) {
        switch (field) {
            case QUOTA:
                if (value == null) {
                    unsetQuota();
                } else {
                    setQuota((ResourceAggregate) value);
                }
                break;

            case PROD_SHARED_CONSUMPTION:
                if (value == null) {
                    unsetProdSharedConsumption();
                } else {
                    setProdSharedConsumption((ResourceAggregate) value);
                }
                break;

            case NON_PROD_SHARED_CONSUMPTION:
                if (value == null) {
                    unsetNonProdSharedConsumption();
                } else {
                    setNonProdSharedConsumption((ResourceAggregate) value);
                }
                break;

            case PROD_DEDICATED_CONSUMPTION:
                if (value == null) {
                    unsetProdDedicatedConsumption();
                } else {
                    setProdDedicatedConsumption((ResourceAggregate) value);
                }
                break;

            case NON_PROD_DEDICATED_CONSUMPTION:
                if (value == null) {
                    unsetNonProdDedicatedConsumption();
                } else {
                    setNonProdDedicatedConsumption((ResourceAggregate) value);
                }
                break;

        }
    }

    public Object getFieldValue(_Fields field) {
        switch (field) {
            case QUOTA:
                return getQuota();

            case PROD_SHARED_CONSUMPTION:
                return getProdSharedConsumption();

            case NON_PROD_SHARED_CONSUMPTION:
                return getNonProdSharedConsumption();

            case PROD_DEDICATED_CONSUMPTION:
                return getProdDedicatedConsumption();

            case NON_PROD_DEDICATED_CONSUMPTION:
                return getNonProdDedicatedConsumption();

        }
        throw new IllegalStateException();
    }

    /**
     * Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise
     */
    public boolean isSet(_Fields field) {
        if (field == null) {
            throw new IllegalArgumentException();
        }

        switch (field) {
            case QUOTA:
                return isSetQuota();
            case PROD_SHARED_CONSUMPTION:
                return isSetProdSharedConsumption();
            case NON_PROD_SHARED_CONSUMPTION:
                return isSetNonProdSharedConsumption();
            case PROD_DEDICATED_CONSUMPTION:
                return isSetProdDedicatedConsumption();
            case NON_PROD_DEDICATED_CONSUMPTION:
                return isSetNonProdDedicatedConsumption();
        }
        throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
        if (that == null)
            return false;
        if (that instanceof GetQuotaResult)
            return this.equals((GetQuotaResult) that);
        return false;
    }

    public boolean equals(GetQuotaResult that) {
        if (that == null)
            return false;

        boolean this_present_quota = true && this.isSetQuota();
        boolean that_present_quota = true && that.isSetQuota();
        if (this_present_quota || that_present_quota) {
            if (!(this_present_quota && that_present_quota))
                return false;
            if (!this.quota.equals(that.quota))
                return false;
        }

        boolean this_present_prodSharedConsumption = true && this.isSetProdSharedConsumption();
        boolean that_present_prodSharedConsumption = true && that.isSetProdSharedConsumption();
        if (this_present_prodSharedConsumption || that_present_prodSharedConsumption) {
            if (!(this_present_prodSharedConsumption && that_present_prodSharedConsumption))
                return false;
            if (!this.prodSharedConsumption.equals(that.prodSharedConsumption))
                return false;
        }

        boolean this_present_nonProdSharedConsumption = true && this.isSetNonProdSharedConsumption();
        boolean that_present_nonProdSharedConsumption = true && that.isSetNonProdSharedConsumption();
        if (this_present_nonProdSharedConsumption || that_present_nonProdSharedConsumption) {
            if (!(this_present_nonProdSharedConsumption && that_present_nonProdSharedConsumption))
                return false;
            if (!this.nonProdSharedConsumption.equals(that.nonProdSharedConsumption))
                return false;
        }

        boolean this_present_prodDedicatedConsumption = true && this.isSetProdDedicatedConsumption();
        boolean that_present_prodDedicatedConsumption = true && that.isSetProdDedicatedConsumption();
        if (this_present_prodDedicatedConsumption || that_present_prodDedicatedConsumption) {
            if (!(this_present_prodDedicatedConsumption && that_present_prodDedicatedConsumption))
                return false;
            if (!this.prodDedicatedConsumption.equals(that.prodDedicatedConsumption))
                return false;
        }

        boolean this_present_nonProdDedicatedConsumption = true && this.isSetNonProdDedicatedConsumption();
        boolean that_present_nonProdDedicatedConsumption = true && that.isSetNonProdDedicatedConsumption();
        if (this_present_nonProdDedicatedConsumption || that_present_nonProdDedicatedConsumption) {
            if (!(this_present_nonProdDedicatedConsumption && that_present_nonProdDedicatedConsumption))
                return false;
            if (!this.nonProdDedicatedConsumption.equals(that.nonProdDedicatedConsumption))
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        List<Object> list = new ArrayList<Object>();

        boolean present_quota = true && (isSetQuota());
        list.add(present_quota);
        if (present_quota)
            list.add(quota);

        boolean present_prodSharedConsumption = true && (isSetProdSharedConsumption());
        list.add(present_prodSharedConsumption);
        if (present_prodSharedConsumption)
            list.add(prodSharedConsumption);

        boolean present_nonProdSharedConsumption = true && (isSetNonProdSharedConsumption());
        list.add(present_nonProdSharedConsumption);
        if (present_nonProdSharedConsumption)
            list.add(nonProdSharedConsumption);

        boolean present_prodDedicatedConsumption = true && (isSetProdDedicatedConsumption());
        list.add(present_prodDedicatedConsumption);
        if (present_prodDedicatedConsumption)
            list.add(prodDedicatedConsumption);

        boolean present_nonProdDedicatedConsumption = true && (isSetNonProdDedicatedConsumption());
        list.add(present_nonProdDedicatedConsumption);
        if (present_nonProdDedicatedConsumption)
            list.add(nonProdDedicatedConsumption);

        return list.hashCode();
    }

    @Override
    public int compareTo(GetQuotaResult other) {
        if (!getClass().equals(other.getClass())) {
            return getClass().getName().compareTo(other.getClass().getName());
        }

        int lastComparison = 0;

        lastComparison = Boolean.valueOf(isSetQuota()).compareTo(other.isSetQuota());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetQuota()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.quota, other.quota);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetProdSharedConsumption()).compareTo(other.isSetProdSharedConsumption());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetProdSharedConsumption()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.prodSharedConsumption, other.prodSharedConsumption);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetNonProdSharedConsumption()).compareTo(other.isSetNonProdSharedConsumption());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetNonProdSharedConsumption()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.nonProdSharedConsumption, other.nonProdSharedConsumption);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetProdDedicatedConsumption()).compareTo(other.isSetProdDedicatedConsumption());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetProdDedicatedConsumption()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.prodDedicatedConsumption, other.prodDedicatedConsumption);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetNonProdDedicatedConsumption()).compareTo(other.isSetNonProdDedicatedConsumption());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetNonProdDedicatedConsumption()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.nonProdDedicatedConsumption, other.nonProdDedicatedConsumption);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        return 0;
    }

    public _Fields fieldForId(int fieldId) {
        return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
        schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
        schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("GetQuotaResult(");
        boolean first = true;

        sb.append("quota:");
        if (this.quota == null) {
            sb.append("null");
        } else {
            sb.append(this.quota);
        }
        first = false;
        if (isSetProdSharedConsumption()) {
            if (!first) sb.append(", ");
            sb.append("prodSharedConsumption:");
            if (this.prodSharedConsumption == null) {
                sb.append("null");
            } else {
                sb.append(this.prodSharedConsumption);
            }
            first = false;
        }
        if (isSetNonProdSharedConsumption()) {
            if (!first) sb.append(", ");
            sb.append("nonProdSharedConsumption:");
            if (this.nonProdSharedConsumption == null) {
                sb.append("null");
            } else {
                sb.append(this.nonProdSharedConsumption);
            }
            first = false;
        }
        if (isSetProdDedicatedConsumption()) {
            if (!first) sb.append(", ");
            sb.append("prodDedicatedConsumption:");
            if (this.prodDedicatedConsumption == null) {
                sb.append("null");
            } else {
                sb.append(this.prodDedicatedConsumption);
            }
            first = false;
        }
        if (isSetNonProdDedicatedConsumption()) {
            if (!first) sb.append(", ");
            sb.append("nonProdDedicatedConsumption:");
            if (this.nonProdDedicatedConsumption == null) {
                sb.append("null");
            } else {
                sb.append(this.nonProdDedicatedConsumption);
            }
            first = false;
        }
        sb.append(")");
        return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
        // check for required fields
        // check for sub-struct validity
        if (quota != null) {
            quota.validate();
        }
        if (prodSharedConsumption != null) {
            prodSharedConsumption.validate();
        }
        if (nonProdSharedConsumption != null) {
            nonProdSharedConsumption.validate();
        }
        if (prodDedicatedConsumption != null) {
            prodDedicatedConsumption.validate();
        }
        if (nonProdDedicatedConsumption != null) {
            nonProdDedicatedConsumption.validate();
        }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
        try {
            write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
        } catch (org.apache.thrift.TException te) {
            throw new java.io.IOException(te);
        }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        try {
            read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
        } catch (org.apache.thrift.TException te) {
            throw new java.io.IOException(te);
        }
    }

    /**
     * The set of fields this struct contains, along with convenience methods for finding and manipulating them.
     */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
        /**
         * Total allocated resource quota.
         */
        QUOTA((short) 1, "quota"),
        /**
         * Resources consumed by production jobs from a shared resource pool.
         */
        PROD_SHARED_CONSUMPTION((short) 2, "prodSharedConsumption"),
        /**
         * Resources consumed by non-production jobs from a shared resource pool.
         */
        NON_PROD_SHARED_CONSUMPTION((short) 3, "nonProdSharedConsumption"),
        /**
         * Resources consumed by production jobs from a dedicated resource pool.
         */
        PROD_DEDICATED_CONSUMPTION((short) 4, "prodDedicatedConsumption"),
        /**
         * Resources consumed by non-production jobs from a dedicated resource pool.
         */
        NON_PROD_DEDICATED_CONSUMPTION((short) 5, "nonProdDedicatedConsumption");

        private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

        static {
            for (_Fields field : EnumSet.allOf(_Fields.class)) {
                byName.put(field.getFieldName(), field);
            }
        }

        private final short _thriftId;
        private final String _fieldName;

        _Fields(short thriftId, String fieldName) {
            _thriftId = thriftId;
            _fieldName = fieldName;
        }

        /**
         * Find the _Fields constant that matches fieldId, or null if its not found.
         */
        public static _Fields findByThriftId(int fieldId) {
            switch (fieldId) {
                case 1: // QUOTA
                    return QUOTA;
                case 2: // PROD_SHARED_CONSUMPTION
                    return PROD_SHARED_CONSUMPTION;
                case 3: // NON_PROD_SHARED_CONSUMPTION
                    return NON_PROD_SHARED_CONSUMPTION;
                case 4: // PROD_DEDICATED_CONSUMPTION
                    return PROD_DEDICATED_CONSUMPTION;
                case 5: // NON_PROD_DEDICATED_CONSUMPTION
                    return NON_PROD_DEDICATED_CONSUMPTION;
                default:
                    return null;
            }
        }

        /**
         * Find the _Fields constant that matches fieldId, throwing an exception
         * if it is not found.
         */
        public static _Fields findByThriftIdOrThrow(int fieldId) {
            _Fields fields = findByThriftId(fieldId);
            if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
            return fields;
        }

        /**
         * Find the _Fields constant that matches name, or null if its not found.
         */
        public static _Fields findByName(String name) {
            return byName.get(name);
        }

        public short getThriftFieldId() {
            return _thriftId;
        }

        public String getFieldName() {
            return _fieldName;
        }
    }

    private static class GetQuotaResultStandardSchemeFactory implements SchemeFactory {
        public GetQuotaResultStandardScheme getScheme() {
            return new GetQuotaResultStandardScheme();
        }
    }

    private static class GetQuotaResultStandardScheme extends StandardScheme<GetQuotaResult> {

        public void read(org.apache.thrift.protocol.TProtocol iprot, GetQuotaResult struct) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField schemeField;
            iprot.readStructBegin();
            while (true) {
                schemeField = iprot.readFieldBegin();
                if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (schemeField.id) {
                    case 1: // QUOTA
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                            struct.quota = new ResourceAggregate();
                            struct.quota.read(iprot);
                            struct.setQuotaIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 2: // PROD_SHARED_CONSUMPTION
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                            struct.prodSharedConsumption = new ResourceAggregate();
                            struct.prodSharedConsumption.read(iprot);
                            struct.setProdSharedConsumptionIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 3: // NON_PROD_SHARED_CONSUMPTION
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                            struct.nonProdSharedConsumption = new ResourceAggregate();
                            struct.nonProdSharedConsumption.read(iprot);
                            struct.setNonProdSharedConsumptionIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 4: // PROD_DEDICATED_CONSUMPTION
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                            struct.prodDedicatedConsumption = new ResourceAggregate();
                            struct.prodDedicatedConsumption.read(iprot);
                            struct.setProdDedicatedConsumptionIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 5: // NON_PROD_DEDICATED_CONSUMPTION
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                            struct.nonProdDedicatedConsumption = new ResourceAggregate();
                            struct.nonProdDedicatedConsumption.read(iprot);
                            struct.setNonProdDedicatedConsumptionIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    default:
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                }
                iprot.readFieldEnd();
            }
            iprot.readStructEnd();

            // check for required fields of primitive type, which can't be checked in the validate method
            struct.validate();
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot, GetQuotaResult struct) throws org.apache.thrift.TException {
            struct.validate();

            oprot.writeStructBegin(STRUCT_DESC);
            if (struct.quota != null) {
                oprot.writeFieldBegin(QUOTA_FIELD_DESC);
                struct.quota.write(oprot);
                oprot.writeFieldEnd();
            }
            if (struct.prodSharedConsumption != null) {
                if (struct.isSetProdSharedConsumption()) {
                    oprot.writeFieldBegin(PROD_SHARED_CONSUMPTION_FIELD_DESC);
                    struct.prodSharedConsumption.write(oprot);
                    oprot.writeFieldEnd();
                }
            }
            if (struct.nonProdSharedConsumption != null) {
                if (struct.isSetNonProdSharedConsumption()) {
                    oprot.writeFieldBegin(NON_PROD_SHARED_CONSUMPTION_FIELD_DESC);
                    struct.nonProdSharedConsumption.write(oprot);
                    oprot.writeFieldEnd();
                }
            }
            if (struct.prodDedicatedConsumption != null) {
                if (struct.isSetProdDedicatedConsumption()) {
                    oprot.writeFieldBegin(PROD_DEDICATED_CONSUMPTION_FIELD_DESC);
                    struct.prodDedicatedConsumption.write(oprot);
                    oprot.writeFieldEnd();
                }
            }
            if (struct.nonProdDedicatedConsumption != null) {
                if (struct.isSetNonProdDedicatedConsumption()) {
                    oprot.writeFieldBegin(NON_PROD_DEDICATED_CONSUMPTION_FIELD_DESC);
                    struct.nonProdDedicatedConsumption.write(oprot);
                    oprot.writeFieldEnd();
                }
            }
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

    }

    private static class GetQuotaResultTupleSchemeFactory implements SchemeFactory {
        public GetQuotaResultTupleScheme getScheme() {
            return new GetQuotaResultTupleScheme();
        }
    }

    private static class GetQuotaResultTupleScheme extends TupleScheme<GetQuotaResult> {

        @Override
        public void write(org.apache.thrift.protocol.TProtocol prot, GetQuotaResult struct) throws org.apache.thrift.TException {
            TTupleProtocol oprot = (TTupleProtocol) prot;
            BitSet optionals = new BitSet();
            if (struct.isSetQuota()) {
                optionals.set(0);
            }
            if (struct.isSetProdSharedConsumption()) {
                optionals.set(1);
            }
            if (struct.isSetNonProdSharedConsumption()) {
                optionals.set(2);
            }
            if (struct.isSetProdDedicatedConsumption()) {
                optionals.set(3);
            }
            if (struct.isSetNonProdDedicatedConsumption()) {
                optionals.set(4);
            }
            oprot.writeBitSet(optionals, 5);
            if (struct.isSetQuota()) {
                struct.quota.write(oprot);
            }
            if (struct.isSetProdSharedConsumption()) {
                struct.prodSharedConsumption.write(oprot);
            }
            if (struct.isSetNonProdSharedConsumption()) {
                struct.nonProdSharedConsumption.write(oprot);
            }
            if (struct.isSetProdDedicatedConsumption()) {
                struct.prodDedicatedConsumption.write(oprot);
            }
            if (struct.isSetNonProdDedicatedConsumption()) {
                struct.nonProdDedicatedConsumption.write(oprot);
            }
        }

        @Override
        public void read(org.apache.thrift.protocol.TProtocol prot, GetQuotaResult struct) throws org.apache.thrift.TException {
            TTupleProtocol iprot = (TTupleProtocol) prot;
            BitSet incoming = iprot.readBitSet(5);
            if (incoming.get(0)) {
                struct.quota = new ResourceAggregate();
                struct.quota.read(iprot);
                struct.setQuotaIsSet(true);
            }
            if (incoming.get(1)) {
                struct.prodSharedConsumption = new ResourceAggregate();
                struct.prodSharedConsumption.read(iprot);
                struct.setProdSharedConsumptionIsSet(true);
            }
            if (incoming.get(2)) {
                struct.nonProdSharedConsumption = new ResourceAggregate();
                struct.nonProdSharedConsumption.read(iprot);
                struct.setNonProdSharedConsumptionIsSet(true);
            }
            if (incoming.get(3)) {
                struct.prodDedicatedConsumption = new ResourceAggregate();
                struct.prodDedicatedConsumption.read(iprot);
                struct.setProdDedicatedConsumptionIsSet(true);
            }
            if (incoming.get(4)) {
                struct.nonProdDedicatedConsumption = new ResourceAggregate();
                struct.nonProdDedicatedConsumption.read(iprot);
                struct.setNonProdDedicatedConsumptionIsSet(true);
            }
        }
    }

}

