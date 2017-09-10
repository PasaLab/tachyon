/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package alluxio.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-09-08")
public class GetWorkerIdTResponse implements org.apache.thrift.TBase<GetWorkerIdTResponse, GetWorkerIdTResponse._Fields>, java.io.Serializable, Cloneable, Comparable<GetWorkerIdTResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetWorkerIdTResponse");

  private static final org.apache.thrift.protocol.TField WORKER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("workerId", org.apache.thrift.protocol.TType.I64, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new GetWorkerIdTResponseStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new GetWorkerIdTResponseTupleSchemeFactory();

  public long workerId; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    WORKER_ID((short)1, "workerId");

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
        case 1: // WORKER_ID
          return WORKER_ID;
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
  private static final int __WORKERID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.WORKER_ID, new org.apache.thrift.meta_data.FieldMetaData("workerId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetWorkerIdTResponse.class, metaDataMap);
  }

  public GetWorkerIdTResponse() {
  }

  public GetWorkerIdTResponse(
    long workerId)
  {
    this();
    this.workerId = workerId;
    setWorkerIdIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GetWorkerIdTResponse(GetWorkerIdTResponse other) {
    __isset_bitfield = other.__isset_bitfield;
    this.workerId = other.workerId;
  }

  public GetWorkerIdTResponse deepCopy() {
    return new GetWorkerIdTResponse(this);
  }

  @Override
  public void clear() {
    setWorkerIdIsSet(false);
    this.workerId = 0;
  }

  public long getWorkerId() {
    return this.workerId;
  }

  public GetWorkerIdTResponse setWorkerId(long workerId) {
    this.workerId = workerId;
    setWorkerIdIsSet(true);
    return this;
  }

  public void unsetWorkerId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __WORKERID_ISSET_ID);
  }

  /** Returns true if field workerId is set (has been assigned a value) and false otherwise */
  public boolean isSetWorkerId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __WORKERID_ISSET_ID);
  }

  public void setWorkerIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __WORKERID_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case WORKER_ID:
      if (value == null) {
        unsetWorkerId();
      } else {
        setWorkerId((java.lang.Long)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case WORKER_ID:
      return getWorkerId();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case WORKER_ID:
      return isSetWorkerId();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof GetWorkerIdTResponse)
      return this.equals((GetWorkerIdTResponse)that);
    return false;
  }

  public boolean equals(GetWorkerIdTResponse that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_workerId = true;
    boolean that_present_workerId = true;
    if (this_present_workerId || that_present_workerId) {
      if (!(this_present_workerId && that_present_workerId))
        return false;
      if (this.workerId != that.workerId)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(workerId);

    return hashCode;
  }

  @Override
  public int compareTo(GetWorkerIdTResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetWorkerId()).compareTo(other.isSetWorkerId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetWorkerId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.workerId, other.workerId);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("GetWorkerIdTResponse(");
    boolean first = true;

    sb.append("workerId:");
    sb.append(this.workerId);
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

  private static class GetWorkerIdTResponseStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GetWorkerIdTResponseStandardScheme getScheme() {
      return new GetWorkerIdTResponseStandardScheme();
    }
  }

  private static class GetWorkerIdTResponseStandardScheme extends org.apache.thrift.scheme.StandardScheme<GetWorkerIdTResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GetWorkerIdTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // WORKER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.workerId = iprot.readI64();
              struct.setWorkerIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, GetWorkerIdTResponse struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(WORKER_ID_FIELD_DESC);
      oprot.writeI64(struct.workerId);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GetWorkerIdTResponseTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GetWorkerIdTResponseTupleScheme getScheme() {
      return new GetWorkerIdTResponseTupleScheme();
    }
  }

  private static class GetWorkerIdTResponseTupleScheme extends org.apache.thrift.scheme.TupleScheme<GetWorkerIdTResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GetWorkerIdTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetWorkerId()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetWorkerId()) {
        oprot.writeI64(struct.workerId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GetWorkerIdTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.workerId = iprot.readI64();
        struct.setWorkerIdIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

