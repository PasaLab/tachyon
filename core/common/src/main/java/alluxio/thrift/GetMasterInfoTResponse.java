/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package alluxio.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-09-14")
public class GetMasterInfoTResponse implements org.apache.thrift.TBase<GetMasterInfoTResponse, GetMasterInfoTResponse._Fields>, java.io.Serializable, Cloneable, Comparable<GetMasterInfoTResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetMasterInfoTResponse");

  private static final org.apache.thrift.protocol.TField MASTER_INFO_FIELD_DESC = new org.apache.thrift.protocol.TField("masterInfo", org.apache.thrift.protocol.TType.STRUCT, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new GetMasterInfoTResponseStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new GetMasterInfoTResponseTupleSchemeFactory();

  public MasterInfo masterInfo; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    MASTER_INFO((short)1, "masterInfo");

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
        case 1: // MASTER_INFO
          return MASTER_INFO;
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
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.MASTER_INFO, new org.apache.thrift.meta_data.FieldMetaData("masterInfo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT        , "MasterInfo")));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetMasterInfoTResponse.class, metaDataMap);
  }

  public GetMasterInfoTResponse() {
  }

  public GetMasterInfoTResponse(
    MasterInfo masterInfo)
  {
    this();
    this.masterInfo = masterInfo;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GetMasterInfoTResponse(GetMasterInfoTResponse other) {
    if (other.isSetMasterInfo()) {
      this.masterInfo = new MasterInfo(other.masterInfo);
    }
  }

  public GetMasterInfoTResponse deepCopy() {
    return new GetMasterInfoTResponse(this);
  }

  @Override
  public void clear() {
    this.masterInfo = null;
  }

  public MasterInfo getMasterInfo() {
    return this.masterInfo;
  }

  public GetMasterInfoTResponse setMasterInfo(MasterInfo masterInfo) {
    this.masterInfo = masterInfo;
    return this;
  }

  public void unsetMasterInfo() {
    this.masterInfo = null;
  }

  /** Returns true if field masterInfo is set (has been assigned a value) and false otherwise */
  public boolean isSetMasterInfo() {
    return this.masterInfo != null;
  }

  public void setMasterInfoIsSet(boolean value) {
    if (!value) {
      this.masterInfo = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case MASTER_INFO:
      if (value == null) {
        unsetMasterInfo();
      } else {
        setMasterInfo((MasterInfo)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case MASTER_INFO:
      return getMasterInfo();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case MASTER_INFO:
      return isSetMasterInfo();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof GetMasterInfoTResponse)
      return this.equals((GetMasterInfoTResponse)that);
    return false;
  }

  public boolean equals(GetMasterInfoTResponse that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_masterInfo = true && this.isSetMasterInfo();
    boolean that_present_masterInfo = true && that.isSetMasterInfo();
    if (this_present_masterInfo || that_present_masterInfo) {
      if (!(this_present_masterInfo && that_present_masterInfo))
        return false;
      if (!this.masterInfo.equals(that.masterInfo))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetMasterInfo()) ? 131071 : 524287);
    if (isSetMasterInfo())
      hashCode = hashCode * 8191 + masterInfo.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(GetMasterInfoTResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetMasterInfo()).compareTo(other.isSetMasterInfo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMasterInfo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.masterInfo, other.masterInfo);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("GetMasterInfoTResponse(");
    boolean first = true;

    sb.append("masterInfo:");
    if (this.masterInfo == null) {
      sb.append("null");
    } else {
      sb.append(this.masterInfo);
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class GetMasterInfoTResponseStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GetMasterInfoTResponseStandardScheme getScheme() {
      return new GetMasterInfoTResponseStandardScheme();
    }
  }

  private static class GetMasterInfoTResponseStandardScheme extends org.apache.thrift.scheme.StandardScheme<GetMasterInfoTResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GetMasterInfoTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // MASTER_INFO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.masterInfo = new MasterInfo();
              struct.masterInfo.read(iprot);
              struct.setMasterInfoIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, GetMasterInfoTResponse struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.masterInfo != null) {
        oprot.writeFieldBegin(MASTER_INFO_FIELD_DESC);
        struct.masterInfo.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GetMasterInfoTResponseTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GetMasterInfoTResponseTupleScheme getScheme() {
      return new GetMasterInfoTResponseTupleScheme();
    }
  }

  private static class GetMasterInfoTResponseTupleScheme extends org.apache.thrift.scheme.TupleScheme<GetMasterInfoTResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GetMasterInfoTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetMasterInfo()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetMasterInfo()) {
        struct.masterInfo.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GetMasterInfoTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.masterInfo = new MasterInfo();
        struct.masterInfo.read(iprot);
        struct.setMasterInfoIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

