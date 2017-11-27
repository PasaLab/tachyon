/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package alluxio.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2017-11-26")
public class MtLRUGetCricTResponse implements org.apache.thrift.TBase<MtLRUGetCricTResponse, MtLRUGetCricTResponse._Fields>, java.io.Serializable, Cloneable, Comparable<MtLRUGetCricTResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MtLRUGetCricTResponse");

  private static final org.apache.thrift.protocol.TField CRIT_VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("critValue", org.apache.thrift.protocol.TType.I64, (short)1);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new MtLRUGetCricTResponseStandardSchemeFactory());
    schemes.put(TupleScheme.class, new MtLRUGetCricTResponseTupleSchemeFactory());
  }

  public long critValue; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CRIT_VALUE((short)1, "critValue");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // CRIT_VALUE
          return CRIT_VALUE;
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

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __CRITVALUE_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CRIT_VALUE, new org.apache.thrift.meta_data.FieldMetaData("critValue", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MtLRUGetCricTResponse.class, metaDataMap);
  }

  public MtLRUGetCricTResponse() {
  }

  public MtLRUGetCricTResponse(
    long critValue)
  {
    this();
    this.critValue = critValue;
    setCritValueIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MtLRUGetCricTResponse(MtLRUGetCricTResponse other) {
    __isset_bitfield = other.__isset_bitfield;
    this.critValue = other.critValue;
  }

  public MtLRUGetCricTResponse deepCopy() {
    return new MtLRUGetCricTResponse(this);
  }

  @Override
  public void clear() {
    setCritValueIsSet(false);
    this.critValue = 0;
  }

  public long getCritValue() {
    return this.critValue;
  }

  public MtLRUGetCricTResponse setCritValue(long critValue) {
    this.critValue = critValue;
    setCritValueIsSet(true);
    return this;
  }

  public void unsetCritValue() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CRITVALUE_ISSET_ID);
  }

  /** Returns true if field critValue is set (has been assigned a value) and false otherwise */
  public boolean isSetCritValue() {
    return EncodingUtils.testBit(__isset_bitfield, __CRITVALUE_ISSET_ID);
  }

  public void setCritValueIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CRITVALUE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case CRIT_VALUE:
      if (value == null) {
        unsetCritValue();
      } else {
        setCritValue((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CRIT_VALUE:
      return getCritValue();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case CRIT_VALUE:
      return isSetCritValue();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof MtLRUGetCricTResponse)
      return this.equals((MtLRUGetCricTResponse)that);
    return false;
  }

  public boolean equals(MtLRUGetCricTResponse that) {
    if (that == null)
      return false;

    boolean this_present_critValue = true;
    boolean that_present_critValue = true;
    if (this_present_critValue || that_present_critValue) {
      if (!(this_present_critValue && that_present_critValue))
        return false;
      if (this.critValue != that.critValue)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_critValue = true;
    list.add(present_critValue);
    if (present_critValue)
      list.add(critValue);

    return list.hashCode();
  }

  @Override
  public int compareTo(MtLRUGetCricTResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetCritValue()).compareTo(other.isSetCritValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCritValue()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.critValue, other.critValue);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("MtLRUGetCricTResponse(");
    boolean first = true;

    sb.append("critValue:");
    sb.append(this.critValue);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class MtLRUGetCricTResponseStandardSchemeFactory implements SchemeFactory {
    public MtLRUGetCricTResponseStandardScheme getScheme() {
      return new MtLRUGetCricTResponseStandardScheme();
    }
  }

  private static class MtLRUGetCricTResponseStandardScheme extends StandardScheme<MtLRUGetCricTResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, MtLRUGetCricTResponse struct) throws TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CRIT_VALUE
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.critValue = iprot.readI64();
              struct.setCritValueIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, MtLRUGetCricTResponse struct) throws TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(CRIT_VALUE_FIELD_DESC);
      oprot.writeI64(struct.critValue);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MtLRUGetCricTResponseTupleSchemeFactory implements SchemeFactory {
    public MtLRUGetCricTResponseTupleScheme getScheme() {
      return new MtLRUGetCricTResponseTupleScheme();
    }
  }

  private static class MtLRUGetCricTResponseTupleScheme extends TupleScheme<MtLRUGetCricTResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MtLRUGetCricTResponse struct) throws TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetCritValue()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetCritValue()) {
        oprot.writeI64(struct.critValue);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MtLRUGetCricTResponse struct) throws TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.critValue = iprot.readI64();
        struct.setCritValueIsSet(true);
      }
    }
  }

}

