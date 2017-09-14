/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package alluxio.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-09-14")
public class GetStatusTResponse implements org.apache.thrift.TBase<GetStatusTResponse, GetStatusTResponse._Fields>, java.io.Serializable, Cloneable, Comparable<GetStatusTResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetStatusTResponse");

  private static final org.apache.thrift.protocol.TField FILE_INFO_FIELD_DESC = new org.apache.thrift.protocol.TField("fileInfo", org.apache.thrift.protocol.TType.STRUCT, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new GetStatusTResponseStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new GetStatusTResponseTupleSchemeFactory();

  public FileInfo fileInfo; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    FILE_INFO((short)1, "fileInfo");

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
        case 1: // FILE_INFO
          return FILE_INFO;
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
    tmpMap.put(_Fields.FILE_INFO, new org.apache.thrift.meta_data.FieldMetaData("fileInfo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT        , "FileInfo")));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetStatusTResponse.class, metaDataMap);
  }

  public GetStatusTResponse() {
  }

  public GetStatusTResponse(
    FileInfo fileInfo)
  {
    this();
    this.fileInfo = fileInfo;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GetStatusTResponse(GetStatusTResponse other) {
    if (other.isSetFileInfo()) {
      this.fileInfo = new FileInfo(other.fileInfo);
    }
  }

  public GetStatusTResponse deepCopy() {
    return new GetStatusTResponse(this);
  }

  @Override
  public void clear() {
    this.fileInfo = null;
  }

  public FileInfo getFileInfo() {
    return this.fileInfo;
  }

  public GetStatusTResponse setFileInfo(FileInfo fileInfo) {
    this.fileInfo = fileInfo;
    return this;
  }

  public void unsetFileInfo() {
    this.fileInfo = null;
  }

  /** Returns true if field fileInfo is set (has been assigned a value) and false otherwise */
  public boolean isSetFileInfo() {
    return this.fileInfo != null;
  }

  public void setFileInfoIsSet(boolean value) {
    if (!value) {
      this.fileInfo = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case FILE_INFO:
      if (value == null) {
        unsetFileInfo();
      } else {
        setFileInfo((FileInfo)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case FILE_INFO:
      return getFileInfo();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case FILE_INFO:
      return isSetFileInfo();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof GetStatusTResponse)
      return this.equals((GetStatusTResponse)that);
    return false;
  }

  public boolean equals(GetStatusTResponse that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_fileInfo = true && this.isSetFileInfo();
    boolean that_present_fileInfo = true && that.isSetFileInfo();
    if (this_present_fileInfo || that_present_fileInfo) {
      if (!(this_present_fileInfo && that_present_fileInfo))
        return false;
      if (!this.fileInfo.equals(that.fileInfo))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetFileInfo()) ? 131071 : 524287);
    if (isSetFileInfo())
      hashCode = hashCode * 8191 + fileInfo.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(GetStatusTResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetFileInfo()).compareTo(other.isSetFileInfo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFileInfo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fileInfo, other.fileInfo);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("GetStatusTResponse(");
    boolean first = true;

    sb.append("fileInfo:");
    if (this.fileInfo == null) {
      sb.append("null");
    } else {
      sb.append(this.fileInfo);
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

  private static class GetStatusTResponseStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GetStatusTResponseStandardScheme getScheme() {
      return new GetStatusTResponseStandardScheme();
    }
  }

  private static class GetStatusTResponseStandardScheme extends org.apache.thrift.scheme.StandardScheme<GetStatusTResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GetStatusTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // FILE_INFO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.fileInfo = new FileInfo();
              struct.fileInfo.read(iprot);
              struct.setFileInfoIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, GetStatusTResponse struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.fileInfo != null) {
        oprot.writeFieldBegin(FILE_INFO_FIELD_DESC);
        struct.fileInfo.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GetStatusTResponseTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GetStatusTResponseTupleScheme getScheme() {
      return new GetStatusTResponseTupleScheme();
    }
  }

  private static class GetStatusTResponseTupleScheme extends org.apache.thrift.scheme.TupleScheme<GetStatusTResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GetStatusTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetFileInfo()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetFileInfo()) {
        struct.fileInfo.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GetStatusTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.fileInfo = new FileInfo();
        struct.fileInfo.read(iprot);
        struct.setFileInfoIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

