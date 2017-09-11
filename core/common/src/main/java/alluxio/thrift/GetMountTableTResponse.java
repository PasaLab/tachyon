/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package alluxio.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-09-11")
public class GetMountTableTResponse implements org.apache.thrift.TBase<GetMountTableTResponse, GetMountTableTResponse._Fields>, java.io.Serializable, Cloneable, Comparable<GetMountTableTResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetMountTableTResponse");

  private static final org.apache.thrift.protocol.TField MOUNT_TABLE_FIELD_DESC = new org.apache.thrift.protocol.TField("mountTable", org.apache.thrift.protocol.TType.MAP, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new GetMountTableTResponseStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new GetMountTableTResponseTupleSchemeFactory();

  public java.util.Map<java.lang.String,MountPointInfo> mountTable; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    MOUNT_TABLE((short)1, "mountTable");

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
        case 1: // MOUNT_TABLE
          return MOUNT_TABLE;
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
    tmpMap.put(_Fields.MOUNT_TABLE, new org.apache.thrift.meta_data.FieldMetaData("mountTable", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "MountPointInfo"))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetMountTableTResponse.class, metaDataMap);
  }

  public GetMountTableTResponse() {
  }

  public GetMountTableTResponse(
    java.util.Map<java.lang.String,MountPointInfo> mountTable)
  {
    this();
    this.mountTable = mountTable;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GetMountTableTResponse(GetMountTableTResponse other) {
    if (other.isSetMountTable()) {
      java.util.Map<java.lang.String,MountPointInfo> __this__mountTable = new java.util.HashMap<java.lang.String,MountPointInfo>(other.mountTable.size());
      for (java.util.Map.Entry<java.lang.String, MountPointInfo> other_element : other.mountTable.entrySet()) {

        java.lang.String other_element_key = other_element.getKey();
        MountPointInfo other_element_value = other_element.getValue();

        java.lang.String __this__mountTable_copy_key = other_element_key;

        MountPointInfo __this__mountTable_copy_value = other_element_value;

        __this__mountTable.put(__this__mountTable_copy_key, __this__mountTable_copy_value);
      }
      this.mountTable = __this__mountTable;
    }
  }

  public GetMountTableTResponse deepCopy() {
    return new GetMountTableTResponse(this);
  }

  @Override
  public void clear() {
    this.mountTable = null;
  }

  public int getMountTableSize() {
    return (this.mountTable == null) ? 0 : this.mountTable.size();
  }

  public void putToMountTable(java.lang.String key, MountPointInfo val) {
    if (this.mountTable == null) {
      this.mountTable = new java.util.HashMap<java.lang.String,MountPointInfo>();
    }
    this.mountTable.put(key, val);
  }

  public java.util.Map<java.lang.String,MountPointInfo> getMountTable() {
    return this.mountTable;
  }

  public GetMountTableTResponse setMountTable(java.util.Map<java.lang.String,MountPointInfo> mountTable) {
    this.mountTable = mountTable;
    return this;
  }

  public void unsetMountTable() {
    this.mountTable = null;
  }

  /** Returns true if field mountTable is set (has been assigned a value) and false otherwise */
  public boolean isSetMountTable() {
    return this.mountTable != null;
  }

  public void setMountTableIsSet(boolean value) {
    if (!value) {
      this.mountTable = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case MOUNT_TABLE:
      if (value == null) {
        unsetMountTable();
      } else {
        setMountTable((java.util.Map<java.lang.String,MountPointInfo>)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case MOUNT_TABLE:
      return getMountTable();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case MOUNT_TABLE:
      return isSetMountTable();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof GetMountTableTResponse)
      return this.equals((GetMountTableTResponse)that);
    return false;
  }

  public boolean equals(GetMountTableTResponse that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_mountTable = true && this.isSetMountTable();
    boolean that_present_mountTable = true && that.isSetMountTable();
    if (this_present_mountTable || that_present_mountTable) {
      if (!(this_present_mountTable && that_present_mountTable))
        return false;
      if (!this.mountTable.equals(that.mountTable))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetMountTable()) ? 131071 : 524287);
    if (isSetMountTable())
      hashCode = hashCode * 8191 + mountTable.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(GetMountTableTResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetMountTable()).compareTo(other.isSetMountTable());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMountTable()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.mountTable, other.mountTable);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("GetMountTableTResponse(");
    boolean first = true;

    sb.append("mountTable:");
    if (this.mountTable == null) {
      sb.append("null");
    } else {
      sb.append(this.mountTable);
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

  private static class GetMountTableTResponseStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GetMountTableTResponseStandardScheme getScheme() {
      return new GetMountTableTResponseStandardScheme();
    }
  }

  private static class GetMountTableTResponseStandardScheme extends org.apache.thrift.scheme.StandardScheme<GetMountTableTResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GetMountTableTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // MOUNT_TABLE
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map74 = iprot.readMapBegin();
                struct.mountTable = new java.util.HashMap<java.lang.String,MountPointInfo>(2*_map74.size);
                java.lang.String _key75;
                MountPointInfo _val76;
                for (int _i77 = 0; _i77 < _map74.size; ++_i77)
                {
                  _key75 = iprot.readString();
                  _val76 = new MountPointInfo();
                  _val76.read(iprot);
                  struct.mountTable.put(_key75, _val76);
                }
                iprot.readMapEnd();
              }
              struct.setMountTableIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, GetMountTableTResponse struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.mountTable != null) {
        oprot.writeFieldBegin(MOUNT_TABLE_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, struct.mountTable.size()));
          for (java.util.Map.Entry<java.lang.String, MountPointInfo> _iter78 : struct.mountTable.entrySet())
          {
            oprot.writeString(_iter78.getKey());
            _iter78.getValue().write(oprot);
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GetMountTableTResponseTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GetMountTableTResponseTupleScheme getScheme() {
      return new GetMountTableTResponseTupleScheme();
    }
  }

  private static class GetMountTableTResponseTupleScheme extends org.apache.thrift.scheme.TupleScheme<GetMountTableTResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GetMountTableTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetMountTable()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetMountTable()) {
        {
          oprot.writeI32(struct.mountTable.size());
          for (java.util.Map.Entry<java.lang.String, MountPointInfo> _iter79 : struct.mountTable.entrySet())
          {
            oprot.writeString(_iter79.getKey());
            _iter79.getValue().write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GetMountTableTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TMap _map80 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.mountTable = new java.util.HashMap<java.lang.String,MountPointInfo>(2*_map80.size);
          java.lang.String _key81;
          MountPointInfo _val82;
          for (int _i83 = 0; _i83 < _map80.size; ++_i83)
          {
            _key81 = iprot.readString();
            _val82 = new MountPointInfo();
            _val82.read(iprot);
            struct.mountTable.put(_key81, _val82);
          }
        }
        struct.setMountTableIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

