/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package alluxio.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-09-11")
public class LockBlockTResponse implements org.apache.thrift.TBase<LockBlockTResponse, LockBlockTResponse._Fields>, java.io.Serializable, Cloneable, Comparable<LockBlockTResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("LockBlockTResponse");

  private static final org.apache.thrift.protocol.TField LOCK_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("lockId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField BLOCK_PATH_FIELD_DESC = new org.apache.thrift.protocol.TField("blockPath", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField LOCK_BLOCK_STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("lockBlockStatus", org.apache.thrift.protocol.TType.I32, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new LockBlockTResponseStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new LockBlockTResponseTupleSchemeFactory();

  public long lockId; // required
  public java.lang.String blockPath; // required
  public LockBlockStatus lockBlockStatus; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    LOCK_ID((short)1, "lockId"),
    BLOCK_PATH((short)2, "blockPath"),
    LOCK_BLOCK_STATUS((short)3, "lockBlockStatus");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // LOCK_ID
          return LOCK_ID;
        case 2: // BLOCK_PATH
          return BLOCK_PATH;
        case 3: // LOCK_BLOCK_STATUS
          return LOCK_BLOCK_STATUS;
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
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __LOCKID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.LOCK_ID, new org.apache.thrift.meta_data.FieldMetaData("lockId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.BLOCK_PATH, new org.apache.thrift.meta_data.FieldMetaData("blockPath", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.LOCK_BLOCK_STATUS, new org.apache.thrift.meta_data.FieldMetaData("lockBlockStatus", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.ENUM        , "LockBlockStatus")));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(LockBlockTResponse.class, metaDataMap);
  }

  public LockBlockTResponse() {
  }

  public LockBlockTResponse(
    long lockId,
    java.lang.String blockPath,
    LockBlockStatus lockBlockStatus)
  {
    this();
    this.lockId = lockId;
    setLockIdIsSet(true);
    this.blockPath = blockPath;
    this.lockBlockStatus = lockBlockStatus;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public LockBlockTResponse(LockBlockTResponse other) {
    __isset_bitfield = other.__isset_bitfield;
    this.lockId = other.lockId;
    if (other.isSetBlockPath()) {
      this.blockPath = other.blockPath;
    }
    if (other.isSetLockBlockStatus()) {
      this.lockBlockStatus = other.lockBlockStatus;
    }
  }

  public LockBlockTResponse deepCopy() {
    return new LockBlockTResponse(this);
  }

  @Override
  public void clear() {
    setLockIdIsSet(false);
    this.lockId = 0;
    this.blockPath = null;
    this.lockBlockStatus = null;
  }

  public long getLockId() {
    return this.lockId;
  }

  public LockBlockTResponse setLockId(long lockId) {
    this.lockId = lockId;
    setLockIdIsSet(true);
    return this;
  }

  public void unsetLockId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __LOCKID_ISSET_ID);
  }

  /** Returns true if field lockId is set (has been assigned a value) and false otherwise */
  public boolean isSetLockId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __LOCKID_ISSET_ID);
  }

  public void setLockIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __LOCKID_ISSET_ID, value);
  }

  public java.lang.String getBlockPath() {
    return this.blockPath;
  }

  public LockBlockTResponse setBlockPath(java.lang.String blockPath) {
    this.blockPath = blockPath;
    return this;
  }

  public void unsetBlockPath() {
    this.blockPath = null;
  }

  /** Returns true if field blockPath is set (has been assigned a value) and false otherwise */
  public boolean isSetBlockPath() {
    return this.blockPath != null;
  }

  public void setBlockPathIsSet(boolean value) {
    if (!value) {
      this.blockPath = null;
    }
  }

  public LockBlockStatus getLockBlockStatus() {
    return this.lockBlockStatus;
  }

  public LockBlockTResponse setLockBlockStatus(LockBlockStatus lockBlockStatus) {
    this.lockBlockStatus = lockBlockStatus;
    return this;
  }

  public void unsetLockBlockStatus() {
    this.lockBlockStatus = null;
  }

  /** Returns true if field lockBlockStatus is set (has been assigned a value) and false otherwise */
  public boolean isSetLockBlockStatus() {
    return this.lockBlockStatus != null;
  }

  public void setLockBlockStatusIsSet(boolean value) {
    if (!value) {
      this.lockBlockStatus = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case LOCK_ID:
      if (value == null) {
        unsetLockId();
      } else {
        setLockId((java.lang.Long)value);
      }
      break;

    case BLOCK_PATH:
      if (value == null) {
        unsetBlockPath();
      } else {
        setBlockPath((java.lang.String)value);
      }
      break;

    case LOCK_BLOCK_STATUS:
      if (value == null) {
        unsetLockBlockStatus();
      } else {
        setLockBlockStatus((LockBlockStatus)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case LOCK_ID:
      return getLockId();

    case BLOCK_PATH:
      return getBlockPath();

    case LOCK_BLOCK_STATUS:
      return getLockBlockStatus();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case LOCK_ID:
      return isSetLockId();
    case BLOCK_PATH:
      return isSetBlockPath();
    case LOCK_BLOCK_STATUS:
      return isSetLockBlockStatus();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof LockBlockTResponse)
      return this.equals((LockBlockTResponse)that);
    return false;
  }

  public boolean equals(LockBlockTResponse that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_lockId = true;
    boolean that_present_lockId = true;
    if (this_present_lockId || that_present_lockId) {
      if (!(this_present_lockId && that_present_lockId))
        return false;
      if (this.lockId != that.lockId)
        return false;
    }

    boolean this_present_blockPath = true && this.isSetBlockPath();
    boolean that_present_blockPath = true && that.isSetBlockPath();
    if (this_present_blockPath || that_present_blockPath) {
      if (!(this_present_blockPath && that_present_blockPath))
        return false;
      if (!this.blockPath.equals(that.blockPath))
        return false;
    }

    boolean this_present_lockBlockStatus = true && this.isSetLockBlockStatus();
    boolean that_present_lockBlockStatus = true && that.isSetLockBlockStatus();
    if (this_present_lockBlockStatus || that_present_lockBlockStatus) {
      if (!(this_present_lockBlockStatus && that_present_lockBlockStatus))
        return false;
      if (!this.lockBlockStatus.equals(that.lockBlockStatus))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(lockId);

    hashCode = hashCode * 8191 + ((isSetBlockPath()) ? 131071 : 524287);
    if (isSetBlockPath())
      hashCode = hashCode * 8191 + blockPath.hashCode();

    hashCode = hashCode * 8191 + ((isSetLockBlockStatus()) ? 131071 : 524287);
    if (isSetLockBlockStatus())
      hashCode = hashCode * 8191 + lockBlockStatus.getValue();

    return hashCode;
  }

  @Override
  public int compareTo(LockBlockTResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetLockId()).compareTo(other.isSetLockId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLockId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lockId, other.lockId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetBlockPath()).compareTo(other.isSetBlockPath());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBlockPath()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.blockPath, other.blockPath);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetLockBlockStatus()).compareTo(other.isSetLockBlockStatus());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLockBlockStatus()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lockBlockStatus, other.lockBlockStatus);
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
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("LockBlockTResponse(");
    boolean first = true;

    sb.append("lockId:");
    sb.append(this.lockId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("blockPath:");
    if (this.blockPath == null) {
      sb.append("null");
    } else {
      sb.append(this.blockPath);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("lockBlockStatus:");
    if (this.lockBlockStatus == null) {
      sb.append("null");
    } else {
      sb.append(this.lockBlockStatus);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class LockBlockTResponseStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public LockBlockTResponseStandardScheme getScheme() {
      return new LockBlockTResponseStandardScheme();
    }
  }

  private static class LockBlockTResponseStandardScheme extends org.apache.thrift.scheme.StandardScheme<LockBlockTResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, LockBlockTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // LOCK_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.lockId = iprot.readI64();
              struct.setLockIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // BLOCK_PATH
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.blockPath = iprot.readString();
              struct.setBlockPathIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // LOCK_BLOCK_STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.lockBlockStatus = alluxio.thrift.LockBlockStatus.findByValue(iprot.readI32());
              struct.setLockBlockStatusIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, LockBlockTResponse struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(LOCK_ID_FIELD_DESC);
      oprot.writeI64(struct.lockId);
      oprot.writeFieldEnd();
      if (struct.blockPath != null) {
        oprot.writeFieldBegin(BLOCK_PATH_FIELD_DESC);
        oprot.writeString(struct.blockPath);
        oprot.writeFieldEnd();
      }
      if (struct.lockBlockStatus != null) {
        oprot.writeFieldBegin(LOCK_BLOCK_STATUS_FIELD_DESC);
        oprot.writeI32(struct.lockBlockStatus.getValue());
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class LockBlockTResponseTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public LockBlockTResponseTupleScheme getScheme() {
      return new LockBlockTResponseTupleScheme();
    }
  }

  private static class LockBlockTResponseTupleScheme extends org.apache.thrift.scheme.TupleScheme<LockBlockTResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, LockBlockTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetLockId()) {
        optionals.set(0);
      }
      if (struct.isSetBlockPath()) {
        optionals.set(1);
      }
      if (struct.isSetLockBlockStatus()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetLockId()) {
        oprot.writeI64(struct.lockId);
      }
      if (struct.isSetBlockPath()) {
        oprot.writeString(struct.blockPath);
      }
      if (struct.isSetLockBlockStatus()) {
        oprot.writeI32(struct.lockBlockStatus.getValue());
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, LockBlockTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.lockId = iprot.readI64();
        struct.setLockIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.blockPath = iprot.readString();
        struct.setBlockPathIsSet(true);
      }
      if (incoming.get(2)) {
        struct.lockBlockStatus = alluxio.thrift.LockBlockStatus.findByValue(iprot.readI32());
        struct.setLockBlockStatusIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

