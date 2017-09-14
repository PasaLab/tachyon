/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package alluxio.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-09-14")
public class MountTOptions implements org.apache.thrift.TBase<MountTOptions, MountTOptions._Fields>, java.io.Serializable, Cloneable, Comparable<MountTOptions> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MountTOptions");

  private static final org.apache.thrift.protocol.TField READ_ONLY_FIELD_DESC = new org.apache.thrift.protocol.TField("readOnly", org.apache.thrift.protocol.TType.BOOL, (short)1);
  private static final org.apache.thrift.protocol.TField PROPERTIES_FIELD_DESC = new org.apache.thrift.protocol.TField("properties", org.apache.thrift.protocol.TType.MAP, (short)2);
  private static final org.apache.thrift.protocol.TField SHARED_FIELD_DESC = new org.apache.thrift.protocol.TField("shared", org.apache.thrift.protocol.TType.BOOL, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new MountTOptionsStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new MountTOptionsTupleSchemeFactory();

  public boolean readOnly; // optional
  public java.util.Map<java.lang.String,java.lang.String> properties; // optional
  public boolean shared; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    READ_ONLY((short)1, "readOnly"),
    PROPERTIES((short)2, "properties"),
    SHARED((short)3, "shared");

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
        case 1: // READ_ONLY
          return READ_ONLY;
        case 2: // PROPERTIES
          return PROPERTIES;
        case 3: // SHARED
          return SHARED;
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
  private static final int __READONLY_ISSET_ID = 0;
  private static final int __SHARED_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.READ_ONLY,_Fields.PROPERTIES,_Fields.SHARED};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.READ_ONLY, new org.apache.thrift.meta_data.FieldMetaData("readOnly", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.PROPERTIES, new org.apache.thrift.meta_data.FieldMetaData("properties", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.SHARED, new org.apache.thrift.meta_data.FieldMetaData("shared", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MountTOptions.class, metaDataMap);
  }

  public MountTOptions() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MountTOptions(MountTOptions other) {
    __isset_bitfield = other.__isset_bitfield;
    this.readOnly = other.readOnly;
    if (other.isSetProperties()) {
      java.util.Map<java.lang.String,java.lang.String> __this__properties = new java.util.HashMap<java.lang.String,java.lang.String>(other.properties);
      this.properties = __this__properties;
    }
    this.shared = other.shared;
  }

  public MountTOptions deepCopy() {
    return new MountTOptions(this);
  }

  @Override
  public void clear() {
    setReadOnlyIsSet(false);
    this.readOnly = false;
    this.properties = null;
    setSharedIsSet(false);
    this.shared = false;
  }

  public boolean isReadOnly() {
    return this.readOnly;
  }

  public MountTOptions setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
    setReadOnlyIsSet(true);
    return this;
  }

  public void unsetReadOnly() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __READONLY_ISSET_ID);
  }

  /** Returns true if field readOnly is set (has been assigned a value) and false otherwise */
  public boolean isSetReadOnly() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __READONLY_ISSET_ID);
  }

  public void setReadOnlyIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __READONLY_ISSET_ID, value);
  }

  public int getPropertiesSize() {
    return (this.properties == null) ? 0 : this.properties.size();
  }

  public void putToProperties(java.lang.String key, java.lang.String val) {
    if (this.properties == null) {
      this.properties = new java.util.HashMap<java.lang.String,java.lang.String>();
    }
    this.properties.put(key, val);
  }

  public java.util.Map<java.lang.String,java.lang.String> getProperties() {
    return this.properties;
  }

  public MountTOptions setProperties(java.util.Map<java.lang.String,java.lang.String> properties) {
    this.properties = properties;
    return this;
  }

  public void unsetProperties() {
    this.properties = null;
  }

  /** Returns true if field properties is set (has been assigned a value) and false otherwise */
  public boolean isSetProperties() {
    return this.properties != null;
  }

  public void setPropertiesIsSet(boolean value) {
    if (!value) {
      this.properties = null;
    }
  }

  public boolean isShared() {
    return this.shared;
  }

  public MountTOptions setShared(boolean shared) {
    this.shared = shared;
    setSharedIsSet(true);
    return this;
  }

  public void unsetShared() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __SHARED_ISSET_ID);
  }

  /** Returns true if field shared is set (has been assigned a value) and false otherwise */
  public boolean isSetShared() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __SHARED_ISSET_ID);
  }

  public void setSharedIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __SHARED_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case READ_ONLY:
      if (value == null) {
        unsetReadOnly();
      } else {
        setReadOnly((java.lang.Boolean)value);
      }
      break;

    case PROPERTIES:
      if (value == null) {
        unsetProperties();
      } else {
        setProperties((java.util.Map<java.lang.String,java.lang.String>)value);
      }
      break;

    case SHARED:
      if (value == null) {
        unsetShared();
      } else {
        setShared((java.lang.Boolean)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case READ_ONLY:
      return isReadOnly();

    case PROPERTIES:
      return getProperties();

    case SHARED:
      return isShared();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case READ_ONLY:
      return isSetReadOnly();
    case PROPERTIES:
      return isSetProperties();
    case SHARED:
      return isSetShared();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof MountTOptions)
      return this.equals((MountTOptions)that);
    return false;
  }

  public boolean equals(MountTOptions that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_readOnly = true && this.isSetReadOnly();
    boolean that_present_readOnly = true && that.isSetReadOnly();
    if (this_present_readOnly || that_present_readOnly) {
      if (!(this_present_readOnly && that_present_readOnly))
        return false;
      if (this.readOnly != that.readOnly)
        return false;
    }

    boolean this_present_properties = true && this.isSetProperties();
    boolean that_present_properties = true && that.isSetProperties();
    if (this_present_properties || that_present_properties) {
      if (!(this_present_properties && that_present_properties))
        return false;
      if (!this.properties.equals(that.properties))
        return false;
    }

    boolean this_present_shared = true && this.isSetShared();
    boolean that_present_shared = true && that.isSetShared();
    if (this_present_shared || that_present_shared) {
      if (!(this_present_shared && that_present_shared))
        return false;
      if (this.shared != that.shared)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetReadOnly()) ? 131071 : 524287);
    if (isSetReadOnly())
      hashCode = hashCode * 8191 + ((readOnly) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((isSetProperties()) ? 131071 : 524287);
    if (isSetProperties())
      hashCode = hashCode * 8191 + properties.hashCode();

    hashCode = hashCode * 8191 + ((isSetShared()) ? 131071 : 524287);
    if (isSetShared())
      hashCode = hashCode * 8191 + ((shared) ? 131071 : 524287);

    return hashCode;
  }

  @Override
  public int compareTo(MountTOptions other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetReadOnly()).compareTo(other.isSetReadOnly());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReadOnly()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.readOnly, other.readOnly);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetProperties()).compareTo(other.isSetProperties());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProperties()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.properties, other.properties);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetShared()).compareTo(other.isSetShared());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetShared()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.shared, other.shared);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("MountTOptions(");
    boolean first = true;

    if (isSetReadOnly()) {
      sb.append("readOnly:");
      sb.append(this.readOnly);
      first = false;
    }
    if (isSetProperties()) {
      if (!first) sb.append(", ");
      sb.append("properties:");
      if (this.properties == null) {
        sb.append("null");
      } else {
        sb.append(this.properties);
      }
      first = false;
    }
    if (isSetShared()) {
      if (!first) sb.append(", ");
      sb.append("shared:");
      sb.append(this.shared);
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class MountTOptionsStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public MountTOptionsStandardScheme getScheme() {
      return new MountTOptionsStandardScheme();
    }
  }

  private static class MountTOptionsStandardScheme extends org.apache.thrift.scheme.StandardScheme<MountTOptions> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, MountTOptions struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // READ_ONLY
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.readOnly = iprot.readBool();
              struct.setReadOnlyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PROPERTIES
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map64 = iprot.readMapBegin();
                struct.properties = new java.util.HashMap<java.lang.String,java.lang.String>(2*_map64.size);
                java.lang.String _key65;
                java.lang.String _val66;
                for (int _i67 = 0; _i67 < _map64.size; ++_i67)
                {
                  _key65 = iprot.readString();
                  _val66 = iprot.readString();
                  struct.properties.put(_key65, _val66);
                }
                iprot.readMapEnd();
              }
              struct.setPropertiesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SHARED
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.shared = iprot.readBool();
              struct.setSharedIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, MountTOptions struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetReadOnly()) {
        oprot.writeFieldBegin(READ_ONLY_FIELD_DESC);
        oprot.writeBool(struct.readOnly);
        oprot.writeFieldEnd();
      }
      if (struct.properties != null) {
        if (struct.isSetProperties()) {
          oprot.writeFieldBegin(PROPERTIES_FIELD_DESC);
          {
            oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, struct.properties.size()));
            for (java.util.Map.Entry<java.lang.String, java.lang.String> _iter68 : struct.properties.entrySet())
            {
              oprot.writeString(_iter68.getKey());
              oprot.writeString(_iter68.getValue());
            }
            oprot.writeMapEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetShared()) {
        oprot.writeFieldBegin(SHARED_FIELD_DESC);
        oprot.writeBool(struct.shared);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MountTOptionsTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public MountTOptionsTupleScheme getScheme() {
      return new MountTOptionsTupleScheme();
    }
  }

  private static class MountTOptionsTupleScheme extends org.apache.thrift.scheme.TupleScheme<MountTOptions> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MountTOptions struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetReadOnly()) {
        optionals.set(0);
      }
      if (struct.isSetProperties()) {
        optionals.set(1);
      }
      if (struct.isSetShared()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetReadOnly()) {
        oprot.writeBool(struct.readOnly);
      }
      if (struct.isSetProperties()) {
        {
          oprot.writeI32(struct.properties.size());
          for (java.util.Map.Entry<java.lang.String, java.lang.String> _iter69 : struct.properties.entrySet())
          {
            oprot.writeString(_iter69.getKey());
            oprot.writeString(_iter69.getValue());
          }
        }
      }
      if (struct.isSetShared()) {
        oprot.writeBool(struct.shared);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MountTOptions struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.readOnly = iprot.readBool();
        struct.setReadOnlyIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TMap _map70 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.properties = new java.util.HashMap<java.lang.String,java.lang.String>(2*_map70.size);
          java.lang.String _key71;
          java.lang.String _val72;
          for (int _i73 = 0; _i73 < _map70.size; ++_i73)
          {
            _key71 = iprot.readString();
            _val72 = iprot.readString();
            struct.properties.put(_key71, _val72);
          }
        }
        struct.setPropertiesIsSet(true);
      }
      if (incoming.get(2)) {
        struct.shared = iprot.readBool();
        struct.setSharedIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

