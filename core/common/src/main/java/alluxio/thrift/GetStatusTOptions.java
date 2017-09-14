/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package alluxio.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-09-14")
public class GetStatusTOptions implements org.apache.thrift.TBase<GetStatusTOptions, GetStatusTOptions._Fields>, java.io.Serializable, Cloneable, Comparable<GetStatusTOptions> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetStatusTOptions");

  private static final org.apache.thrift.protocol.TField LOAD_METADATA_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("loadMetadataType", org.apache.thrift.protocol.TType.I32, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new GetStatusTOptionsStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new GetStatusTOptionsTupleSchemeFactory();

  /**
   * 
   * @see LoadMetadataTType
   */
  public LoadMetadataTType loadMetadataType; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 
     * @see LoadMetadataTType
     */
    LOAD_METADATA_TYPE((short)1, "loadMetadataType");

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
        case 1: // LOAD_METADATA_TYPE
          return LOAD_METADATA_TYPE;
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
  private static final _Fields optionals[] = {_Fields.LOAD_METADATA_TYPE};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.LOAD_METADATA_TYPE, new org.apache.thrift.meta_data.FieldMetaData("loadMetadataType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, LoadMetadataTType.class)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetStatusTOptions.class, metaDataMap);
  }

  public GetStatusTOptions() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GetStatusTOptions(GetStatusTOptions other) {
    if (other.isSetLoadMetadataType()) {
      this.loadMetadataType = other.loadMetadataType;
    }
  }

  public GetStatusTOptions deepCopy() {
    return new GetStatusTOptions(this);
  }

  @Override
  public void clear() {
    this.loadMetadataType = null;
  }

  /**
   * 
   * @see LoadMetadataTType
   */
  public LoadMetadataTType getLoadMetadataType() {
    return this.loadMetadataType;
  }

  /**
   * 
   * @see LoadMetadataTType
   */
  public GetStatusTOptions setLoadMetadataType(LoadMetadataTType loadMetadataType) {
    this.loadMetadataType = loadMetadataType;
    return this;
  }

  public void unsetLoadMetadataType() {
    this.loadMetadataType = null;
  }

  /** Returns true if field loadMetadataType is set (has been assigned a value) and false otherwise */
  public boolean isSetLoadMetadataType() {
    return this.loadMetadataType != null;
  }

  public void setLoadMetadataTypeIsSet(boolean value) {
    if (!value) {
      this.loadMetadataType = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case LOAD_METADATA_TYPE:
      if (value == null) {
        unsetLoadMetadataType();
      } else {
        setLoadMetadataType((LoadMetadataTType)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case LOAD_METADATA_TYPE:
      return getLoadMetadataType();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case LOAD_METADATA_TYPE:
      return isSetLoadMetadataType();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof GetStatusTOptions)
      return this.equals((GetStatusTOptions)that);
    return false;
  }

  public boolean equals(GetStatusTOptions that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_loadMetadataType = true && this.isSetLoadMetadataType();
    boolean that_present_loadMetadataType = true && that.isSetLoadMetadataType();
    if (this_present_loadMetadataType || that_present_loadMetadataType) {
      if (!(this_present_loadMetadataType && that_present_loadMetadataType))
        return false;
      if (!this.loadMetadataType.equals(that.loadMetadataType))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetLoadMetadataType()) ? 131071 : 524287);
    if (isSetLoadMetadataType())
      hashCode = hashCode * 8191 + loadMetadataType.getValue();

    return hashCode;
  }

  @Override
  public int compareTo(GetStatusTOptions other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetLoadMetadataType()).compareTo(other.isSetLoadMetadataType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLoadMetadataType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.loadMetadataType, other.loadMetadataType);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("GetStatusTOptions(");
    boolean first = true;

    if (isSetLoadMetadataType()) {
      sb.append("loadMetadataType:");
      if (this.loadMetadataType == null) {
        sb.append("null");
      } else {
        sb.append(this.loadMetadataType);
      }
      first = false;
    }
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

  private static class GetStatusTOptionsStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GetStatusTOptionsStandardScheme getScheme() {
      return new GetStatusTOptionsStandardScheme();
    }
  }

  private static class GetStatusTOptionsStandardScheme extends org.apache.thrift.scheme.StandardScheme<GetStatusTOptions> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GetStatusTOptions struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // LOAD_METADATA_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.loadMetadataType = alluxio.thrift.LoadMetadataTType.findByValue(iprot.readI32());
              struct.setLoadMetadataTypeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, GetStatusTOptions struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.loadMetadataType != null) {
        if (struct.isSetLoadMetadataType()) {
          oprot.writeFieldBegin(LOAD_METADATA_TYPE_FIELD_DESC);
          oprot.writeI32(struct.loadMetadataType.getValue());
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GetStatusTOptionsTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GetStatusTOptionsTupleScheme getScheme() {
      return new GetStatusTOptionsTupleScheme();
    }
  }

  private static class GetStatusTOptionsTupleScheme extends org.apache.thrift.scheme.TupleScheme<GetStatusTOptions> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GetStatusTOptions struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetLoadMetadataType()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetLoadMetadataType()) {
        oprot.writeI32(struct.loadMetadataType.getValue());
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GetStatusTOptions struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.loadMetadataType = alluxio.thrift.LoadMetadataTType.findByValue(iprot.readI32());
        struct.setLoadMetadataTypeIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

