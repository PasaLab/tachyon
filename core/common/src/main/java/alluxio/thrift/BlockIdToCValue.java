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
public class BlockIdToCValue implements org.apache.thrift.TBase<BlockIdToCValue, BlockIdToCValue._Fields>, java.io.Serializable, Cloneable, Comparable<BlockIdToCValue> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BlockIdToCValue");

  private static final org.apache.thrift.protocol.TField BLOCK_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("BlockId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField CVALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("CValue", org.apache.thrift.protocol.TType.I64, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new BlockIdToCValueStandardSchemeFactory());
    schemes.put(TupleScheme.class, new BlockIdToCValueTupleSchemeFactory());
  }

  public long BlockId; // required
  public long CValue; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    BLOCK_ID((short)1, "BlockId"),
    CVALUE((short)2, "CValue");

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
        case 1: // BLOCK_ID
          return BLOCK_ID;
        case 2: // CVALUE
          return CVALUE;
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
  private static final int __BLOCKID_ISSET_ID = 0;
  private static final int __CVALUE_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.BLOCK_ID, new org.apache.thrift.meta_data.FieldMetaData("BlockId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.CVALUE, new org.apache.thrift.meta_data.FieldMetaData("CValue", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BlockIdToCValue.class, metaDataMap);
  }

  public BlockIdToCValue() {
  }

  public BlockIdToCValue(
    long BlockId,
    long CValue)
  {
    this();
    this.BlockId = BlockId;
    setBlockIdIsSet(true);
    this.CValue = CValue;
    setCValueIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BlockIdToCValue(BlockIdToCValue other) {
    __isset_bitfield = other.__isset_bitfield;
    this.BlockId = other.BlockId;
    this.CValue = other.CValue;
  }

  public BlockIdToCValue deepCopy() {
    return new BlockIdToCValue(this);
  }

  @Override
  public void clear() {
    setBlockIdIsSet(false);
    this.BlockId = 0;
    setCValueIsSet(false);
    this.CValue = 0;
  }

  public long getBlockId() {
    return this.BlockId;
  }

  public BlockIdToCValue setBlockId(long BlockId) {
    this.BlockId = BlockId;
    setBlockIdIsSet(true);
    return this;
  }

  public void unsetBlockId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __BLOCKID_ISSET_ID);
  }

  /** Returns true if field BlockId is set (has been assigned a value) and false otherwise */
  public boolean isSetBlockId() {
    return EncodingUtils.testBit(__isset_bitfield, __BLOCKID_ISSET_ID);
  }

  public void setBlockIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __BLOCKID_ISSET_ID, value);
  }

  public long getCValue() {
    return this.CValue;
  }

  public BlockIdToCValue setCValue(long CValue) {
    this.CValue = CValue;
    setCValueIsSet(true);
    return this;
  }

  public void unsetCValue() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CVALUE_ISSET_ID);
  }

  /** Returns true if field CValue is set (has been assigned a value) and false otherwise */
  public boolean isSetCValue() {
    return EncodingUtils.testBit(__isset_bitfield, __CVALUE_ISSET_ID);
  }

  public void setCValueIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CVALUE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case BLOCK_ID:
      if (value == null) {
        unsetBlockId();
      } else {
        setBlockId((Long)value);
      }
      break;

    case CVALUE:
      if (value == null) {
        unsetCValue();
      } else {
        setCValue((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case BLOCK_ID:
      return getBlockId();

    case CVALUE:
      return getCValue();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case BLOCK_ID:
      return isSetBlockId();
    case CVALUE:
      return isSetCValue();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof BlockIdToCValue)
      return this.equals((BlockIdToCValue)that);
    return false;
  }

  public boolean equals(BlockIdToCValue that) {
    if (that == null)
      return false;

    boolean this_present_BlockId = true;
    boolean that_present_BlockId = true;
    if (this_present_BlockId || that_present_BlockId) {
      if (!(this_present_BlockId && that_present_BlockId))
        return false;
      if (this.BlockId != that.BlockId)
        return false;
    }

    boolean this_present_CValue = true;
    boolean that_present_CValue = true;
    if (this_present_CValue || that_present_CValue) {
      if (!(this_present_CValue && that_present_CValue))
        return false;
      if (this.CValue != that.CValue)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_BlockId = true;
    list.add(present_BlockId);
    if (present_BlockId)
      list.add(BlockId);

    boolean present_CValue = true;
    list.add(present_CValue);
    if (present_CValue)
      list.add(CValue);

    return list.hashCode();
  }

  @Override
  public int compareTo(BlockIdToCValue other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetBlockId()).compareTo(other.isSetBlockId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBlockId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.BlockId, other.BlockId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCValue()).compareTo(other.isSetCValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCValue()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.CValue, other.CValue);
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
    StringBuilder sb = new StringBuilder("BlockIdToCValue(");
    boolean first = true;

    sb.append("BlockId:");
    sb.append(this.BlockId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("CValue:");
    sb.append(this.CValue);
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

  private static class BlockIdToCValueStandardSchemeFactory implements SchemeFactory {
    public BlockIdToCValueStandardScheme getScheme() {
      return new BlockIdToCValueStandardScheme();
    }
  }

  private static class BlockIdToCValueStandardScheme extends StandardScheme<BlockIdToCValue> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BlockIdToCValue struct) throws TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // BLOCK_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.BlockId = iprot.readI64();
              struct.setBlockIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // CVALUE
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.CValue = iprot.readI64();
              struct.setCValueIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, BlockIdToCValue struct) throws TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(BLOCK_ID_FIELD_DESC);
      oprot.writeI64(struct.BlockId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(CVALUE_FIELD_DESC);
      oprot.writeI64(struct.CValue);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class BlockIdToCValueTupleSchemeFactory implements SchemeFactory {
    public BlockIdToCValueTupleScheme getScheme() {
      return new BlockIdToCValueTupleScheme();
    }
  }

  private static class BlockIdToCValueTupleScheme extends TupleScheme<BlockIdToCValue> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BlockIdToCValue struct) throws TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetBlockId()) {
        optionals.set(0);
      }
      if (struct.isSetCValue()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetBlockId()) {
        oprot.writeI64(struct.BlockId);
      }
      if (struct.isSetCValue()) {
        oprot.writeI64(struct.CValue);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, BlockIdToCValue struct) throws TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.BlockId = iprot.readI64();
        struct.setBlockIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.CValue = iprot.readI64();
        struct.setCValueIsSet(true);
      }
    }
  }

}

