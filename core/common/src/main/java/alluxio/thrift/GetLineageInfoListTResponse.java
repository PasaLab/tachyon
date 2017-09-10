/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package alluxio.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-09-08")
public class GetLineageInfoListTResponse implements org.apache.thrift.TBase<GetLineageInfoListTResponse, GetLineageInfoListTResponse._Fields>, java.io.Serializable, Cloneable, Comparable<GetLineageInfoListTResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetLineageInfoListTResponse");

  private static final org.apache.thrift.protocol.TField LINEAGE_INFO_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("lineageInfoList", org.apache.thrift.protocol.TType.LIST, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new GetLineageInfoListTResponseStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new GetLineageInfoListTResponseTupleSchemeFactory();

  public java.util.List<LineageInfo> lineageInfoList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    LINEAGE_INFO_LIST((short)1, "lineageInfoList");

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
        case 1: // LINEAGE_INFO_LIST
          return LINEAGE_INFO_LIST;
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
    tmpMap.put(_Fields.LINEAGE_INFO_LIST, new org.apache.thrift.meta_data.FieldMetaData("lineageInfoList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, LineageInfo.class))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetLineageInfoListTResponse.class, metaDataMap);
  }

  public GetLineageInfoListTResponse() {
  }

  public GetLineageInfoListTResponse(
    java.util.List<LineageInfo> lineageInfoList)
  {
    this();
    this.lineageInfoList = lineageInfoList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GetLineageInfoListTResponse(GetLineageInfoListTResponse other) {
    if (other.isSetLineageInfoList()) {
      java.util.List<LineageInfo> __this__lineageInfoList = new java.util.ArrayList<LineageInfo>(other.lineageInfoList.size());
      for (LineageInfo other_element : other.lineageInfoList) {
        __this__lineageInfoList.add(new LineageInfo(other_element));
      }
      this.lineageInfoList = __this__lineageInfoList;
    }
  }

  public GetLineageInfoListTResponse deepCopy() {
    return new GetLineageInfoListTResponse(this);
  }

  @Override
  public void clear() {
    this.lineageInfoList = null;
  }

  public int getLineageInfoListSize() {
    return (this.lineageInfoList == null) ? 0 : this.lineageInfoList.size();
  }

  public java.util.Iterator<LineageInfo> getLineageInfoListIterator() {
    return (this.lineageInfoList == null) ? null : this.lineageInfoList.iterator();
  }

  public void addToLineageInfoList(LineageInfo elem) {
    if (this.lineageInfoList == null) {
      this.lineageInfoList = new java.util.ArrayList<LineageInfo>();
    }
    this.lineageInfoList.add(elem);
  }

  public java.util.List<LineageInfo> getLineageInfoList() {
    return this.lineageInfoList;
  }

  public GetLineageInfoListTResponse setLineageInfoList(java.util.List<LineageInfo> lineageInfoList) {
    this.lineageInfoList = lineageInfoList;
    return this;
  }

  public void unsetLineageInfoList() {
    this.lineageInfoList = null;
  }

  /** Returns true if field lineageInfoList is set (has been assigned a value) and false otherwise */
  public boolean isSetLineageInfoList() {
    return this.lineageInfoList != null;
  }

  public void setLineageInfoListIsSet(boolean value) {
    if (!value) {
      this.lineageInfoList = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case LINEAGE_INFO_LIST:
      if (value == null) {
        unsetLineageInfoList();
      } else {
        setLineageInfoList((java.util.List<LineageInfo>)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case LINEAGE_INFO_LIST:
      return getLineageInfoList();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case LINEAGE_INFO_LIST:
      return isSetLineageInfoList();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof GetLineageInfoListTResponse)
      return this.equals((GetLineageInfoListTResponse)that);
    return false;
  }

  public boolean equals(GetLineageInfoListTResponse that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_lineageInfoList = true && this.isSetLineageInfoList();
    boolean that_present_lineageInfoList = true && that.isSetLineageInfoList();
    if (this_present_lineageInfoList || that_present_lineageInfoList) {
      if (!(this_present_lineageInfoList && that_present_lineageInfoList))
        return false;
      if (!this.lineageInfoList.equals(that.lineageInfoList))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetLineageInfoList()) ? 131071 : 524287);
    if (isSetLineageInfoList())
      hashCode = hashCode * 8191 + lineageInfoList.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(GetLineageInfoListTResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetLineageInfoList()).compareTo(other.isSetLineageInfoList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLineageInfoList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lineageInfoList, other.lineageInfoList);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("GetLineageInfoListTResponse(");
    boolean first = true;

    sb.append("lineageInfoList:");
    if (this.lineageInfoList == null) {
      sb.append("null");
    } else {
      sb.append(this.lineageInfoList);
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

  private static class GetLineageInfoListTResponseStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GetLineageInfoListTResponseStandardScheme getScheme() {
      return new GetLineageInfoListTResponseStandardScheme();
    }
  }

  private static class GetLineageInfoListTResponseStandardScheme extends org.apache.thrift.scheme.StandardScheme<GetLineageInfoListTResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GetLineageInfoListTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // LINEAGE_INFO_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list32 = iprot.readListBegin();
                struct.lineageInfoList = new java.util.ArrayList<LineageInfo>(_list32.size);
                LineageInfo _elem33;
                for (int _i34 = 0; _i34 < _list32.size; ++_i34)
                {
                  _elem33 = new LineageInfo();
                  _elem33.read(iprot);
                  struct.lineageInfoList.add(_elem33);
                }
                iprot.readListEnd();
              }
              struct.setLineageInfoListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, GetLineageInfoListTResponse struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.lineageInfoList != null) {
        oprot.writeFieldBegin(LINEAGE_INFO_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.lineageInfoList.size()));
          for (LineageInfo _iter35 : struct.lineageInfoList)
          {
            _iter35.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GetLineageInfoListTResponseTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GetLineageInfoListTResponseTupleScheme getScheme() {
      return new GetLineageInfoListTResponseTupleScheme();
    }
  }

  private static class GetLineageInfoListTResponseTupleScheme extends org.apache.thrift.scheme.TupleScheme<GetLineageInfoListTResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GetLineageInfoListTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetLineageInfoList()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetLineageInfoList()) {
        {
          oprot.writeI32(struct.lineageInfoList.size());
          for (LineageInfo _iter36 : struct.lineageInfoList)
          {
            _iter36.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GetLineageInfoListTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list37 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.lineageInfoList = new java.util.ArrayList<LineageInfo>(_list37.size);
          LineageInfo _elem38;
          for (int _i39 = 0; _i39 < _list37.size; ++_i39)
          {
            _elem38 = new LineageInfo();
            _elem38.read(iprot);
            struct.lineageInfoList.add(_elem38);
          }
        }
        struct.setLineageInfoListIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

