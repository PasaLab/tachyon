/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package alluxio.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-09-08")
public class ListStatusTResponse implements org.apache.thrift.TBase<ListStatusTResponse, ListStatusTResponse._Fields>, java.io.Serializable, Cloneable, Comparable<ListStatusTResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ListStatusTResponse");

  private static final org.apache.thrift.protocol.TField FILE_INFO_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("fileInfoList", org.apache.thrift.protocol.TType.LIST, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new ListStatusTResponseStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new ListStatusTResponseTupleSchemeFactory();

  public java.util.List<FileInfo> fileInfoList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    FILE_INFO_LIST((short)1, "fileInfoList");

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
        case 1: // FILE_INFO_LIST
          return FILE_INFO_LIST;
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
    tmpMap.put(_Fields.FILE_INFO_LIST, new org.apache.thrift.meta_data.FieldMetaData("fileInfoList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "FileInfo"))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ListStatusTResponse.class, metaDataMap);
  }

  public ListStatusTResponse() {
  }

  public ListStatusTResponse(
    java.util.List<FileInfo> fileInfoList)
  {
    this();
    this.fileInfoList = fileInfoList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ListStatusTResponse(ListStatusTResponse other) {
    if (other.isSetFileInfoList()) {
      java.util.List<FileInfo> __this__fileInfoList = new java.util.ArrayList<FileInfo>(other.fileInfoList.size());
      for (FileInfo other_element : other.fileInfoList) {
        __this__fileInfoList.add(other_element);
      }
      this.fileInfoList = __this__fileInfoList;
    }
  }

  public ListStatusTResponse deepCopy() {
    return new ListStatusTResponse(this);
  }

  @Override
  public void clear() {
    this.fileInfoList = null;
  }

  public int getFileInfoListSize() {
    return (this.fileInfoList == null) ? 0 : this.fileInfoList.size();
  }

  public java.util.Iterator<FileInfo> getFileInfoListIterator() {
    return (this.fileInfoList == null) ? null : this.fileInfoList.iterator();
  }

  public void addToFileInfoList(FileInfo elem) {
    if (this.fileInfoList == null) {
      this.fileInfoList = new java.util.ArrayList<FileInfo>();
    }
    this.fileInfoList.add(elem);
  }

  public java.util.List<FileInfo> getFileInfoList() {
    return this.fileInfoList;
  }

  public ListStatusTResponse setFileInfoList(java.util.List<FileInfo> fileInfoList) {
    this.fileInfoList = fileInfoList;
    return this;
  }

  public void unsetFileInfoList() {
    this.fileInfoList = null;
  }

  /** Returns true if field fileInfoList is set (has been assigned a value) and false otherwise */
  public boolean isSetFileInfoList() {
    return this.fileInfoList != null;
  }

  public void setFileInfoListIsSet(boolean value) {
    if (!value) {
      this.fileInfoList = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case FILE_INFO_LIST:
      if (value == null) {
        unsetFileInfoList();
      } else {
        setFileInfoList((java.util.List<FileInfo>)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case FILE_INFO_LIST:
      return getFileInfoList();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case FILE_INFO_LIST:
      return isSetFileInfoList();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof ListStatusTResponse)
      return this.equals((ListStatusTResponse)that);
    return false;
  }

  public boolean equals(ListStatusTResponse that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_fileInfoList = true && this.isSetFileInfoList();
    boolean that_present_fileInfoList = true && that.isSetFileInfoList();
    if (this_present_fileInfoList || that_present_fileInfoList) {
      if (!(this_present_fileInfoList && that_present_fileInfoList))
        return false;
      if (!this.fileInfoList.equals(that.fileInfoList))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetFileInfoList()) ? 131071 : 524287);
    if (isSetFileInfoList())
      hashCode = hashCode * 8191 + fileInfoList.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(ListStatusTResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetFileInfoList()).compareTo(other.isSetFileInfoList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFileInfoList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fileInfoList, other.fileInfoList);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("ListStatusTResponse(");
    boolean first = true;

    sb.append("fileInfoList:");
    if (this.fileInfoList == null) {
      sb.append("null");
    } else {
      sb.append(this.fileInfoList);
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

  private static class ListStatusTResponseStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ListStatusTResponseStandardScheme getScheme() {
      return new ListStatusTResponseStandardScheme();
    }
  }

  private static class ListStatusTResponseStandardScheme extends org.apache.thrift.scheme.StandardScheme<ListStatusTResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ListStatusTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // FILE_INFO_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
                struct.fileInfoList = new java.util.ArrayList<FileInfo>(_list8.size);
                FileInfo _elem9;
                for (int _i10 = 0; _i10 < _list8.size; ++_i10)
                {
                  _elem9 = new FileInfo();
                  _elem9.read(iprot);
                  struct.fileInfoList.add(_elem9);
                }
                iprot.readListEnd();
              }
              struct.setFileInfoListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ListStatusTResponse struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.fileInfoList != null) {
        oprot.writeFieldBegin(FILE_INFO_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.fileInfoList.size()));
          for (FileInfo _iter11 : struct.fileInfoList)
          {
            _iter11.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ListStatusTResponseTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ListStatusTResponseTupleScheme getScheme() {
      return new ListStatusTResponseTupleScheme();
    }
  }

  private static class ListStatusTResponseTupleScheme extends org.apache.thrift.scheme.TupleScheme<ListStatusTResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ListStatusTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetFileInfoList()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetFileInfoList()) {
        {
          oprot.writeI32(struct.fileInfoList.size());
          for (FileInfo _iter12 : struct.fileInfoList)
          {
            _iter12.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ListStatusTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list13 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.fileInfoList = new java.util.ArrayList<FileInfo>(_list13.size);
          FileInfo _elem14;
          for (int _i15 = 0; _i15 < _list13.size; ++_i15)
          {
            _elem14 = new FileInfo();
            _elem14.read(iprot);
            struct.fileInfoList.add(_elem14);
          }
        }
        struct.setFileInfoListIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

