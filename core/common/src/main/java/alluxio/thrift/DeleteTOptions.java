/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package alluxio.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-09-08")
public class DeleteTOptions implements org.apache.thrift.TBase<DeleteTOptions, DeleteTOptions._Fields>, java.io.Serializable, Cloneable, Comparable<DeleteTOptions> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("DeleteTOptions");

  private static final org.apache.thrift.protocol.TField RECURSIVE_FIELD_DESC = new org.apache.thrift.protocol.TField("recursive", org.apache.thrift.protocol.TType.BOOL, (short)1);
  private static final org.apache.thrift.protocol.TField ALLUXIO_ONLY_FIELD_DESC = new org.apache.thrift.protocol.TField("alluxioOnly", org.apache.thrift.protocol.TType.BOOL, (short)2);
  private static final org.apache.thrift.protocol.TField UNCHECKED_FIELD_DESC = new org.apache.thrift.protocol.TField("unchecked", org.apache.thrift.protocol.TType.BOOL, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new DeleteTOptionsStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new DeleteTOptionsTupleSchemeFactory();

  public boolean recursive; // optional
  public boolean alluxioOnly; // optional
  public boolean unchecked; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    RECURSIVE((short)1, "recursive"),
    ALLUXIO_ONLY((short)2, "alluxioOnly"),
    UNCHECKED((short)3, "unchecked");

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
        case 1: // RECURSIVE
          return RECURSIVE;
        case 2: // ALLUXIO_ONLY
          return ALLUXIO_ONLY;
        case 3: // UNCHECKED
          return UNCHECKED;
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
  private static final int __RECURSIVE_ISSET_ID = 0;
  private static final int __ALLUXIOONLY_ISSET_ID = 1;
  private static final int __UNCHECKED_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.RECURSIVE,_Fields.ALLUXIO_ONLY,_Fields.UNCHECKED};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.RECURSIVE, new org.apache.thrift.meta_data.FieldMetaData("recursive", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.ALLUXIO_ONLY, new org.apache.thrift.meta_data.FieldMetaData("alluxioOnly", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.UNCHECKED, new org.apache.thrift.meta_data.FieldMetaData("unchecked", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(DeleteTOptions.class, metaDataMap);
  }

  public DeleteTOptions() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public DeleteTOptions(DeleteTOptions other) {
    __isset_bitfield = other.__isset_bitfield;
    this.recursive = other.recursive;
    this.alluxioOnly = other.alluxioOnly;
    this.unchecked = other.unchecked;
  }

  public DeleteTOptions deepCopy() {
    return new DeleteTOptions(this);
  }

  @Override
  public void clear() {
    setRecursiveIsSet(false);
    this.recursive = false;
    setAlluxioOnlyIsSet(false);
    this.alluxioOnly = false;
    setUncheckedIsSet(false);
    this.unchecked = false;
  }

  public boolean isRecursive() {
    return this.recursive;
  }

  public DeleteTOptions setRecursive(boolean recursive) {
    this.recursive = recursive;
    setRecursiveIsSet(true);
    return this;
  }

  public void unsetRecursive() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __RECURSIVE_ISSET_ID);
  }

  /** Returns true if field recursive is set (has been assigned a value) and false otherwise */
  public boolean isSetRecursive() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __RECURSIVE_ISSET_ID);
  }

  public void setRecursiveIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __RECURSIVE_ISSET_ID, value);
  }

  public boolean isAlluxioOnly() {
    return this.alluxioOnly;
  }

  public DeleteTOptions setAlluxioOnly(boolean alluxioOnly) {
    this.alluxioOnly = alluxioOnly;
    setAlluxioOnlyIsSet(true);
    return this;
  }

  public void unsetAlluxioOnly() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __ALLUXIOONLY_ISSET_ID);
  }

  /** Returns true if field alluxioOnly is set (has been assigned a value) and false otherwise */
  public boolean isSetAlluxioOnly() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __ALLUXIOONLY_ISSET_ID);
  }

  public void setAlluxioOnlyIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __ALLUXIOONLY_ISSET_ID, value);
  }

  public boolean isUnchecked() {
    return this.unchecked;
  }

  public DeleteTOptions setUnchecked(boolean unchecked) {
    this.unchecked = unchecked;
    setUncheckedIsSet(true);
    return this;
  }

  public void unsetUnchecked() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __UNCHECKED_ISSET_ID);
  }

  /** Returns true if field unchecked is set (has been assigned a value) and false otherwise */
  public boolean isSetUnchecked() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __UNCHECKED_ISSET_ID);
  }

  public void setUncheckedIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __UNCHECKED_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case RECURSIVE:
      if (value == null) {
        unsetRecursive();
      } else {
        setRecursive((java.lang.Boolean)value);
      }
      break;

    case ALLUXIO_ONLY:
      if (value == null) {
        unsetAlluxioOnly();
      } else {
        setAlluxioOnly((java.lang.Boolean)value);
      }
      break;

    case UNCHECKED:
      if (value == null) {
        unsetUnchecked();
      } else {
        setUnchecked((java.lang.Boolean)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case RECURSIVE:
      return isRecursive();

    case ALLUXIO_ONLY:
      return isAlluxioOnly();

    case UNCHECKED:
      return isUnchecked();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case RECURSIVE:
      return isSetRecursive();
    case ALLUXIO_ONLY:
      return isSetAlluxioOnly();
    case UNCHECKED:
      return isSetUnchecked();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof DeleteTOptions)
      return this.equals((DeleteTOptions)that);
    return false;
  }

  public boolean equals(DeleteTOptions that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_recursive = true && this.isSetRecursive();
    boolean that_present_recursive = true && that.isSetRecursive();
    if (this_present_recursive || that_present_recursive) {
      if (!(this_present_recursive && that_present_recursive))
        return false;
      if (this.recursive != that.recursive)
        return false;
    }

    boolean this_present_alluxioOnly = true && this.isSetAlluxioOnly();
    boolean that_present_alluxioOnly = true && that.isSetAlluxioOnly();
    if (this_present_alluxioOnly || that_present_alluxioOnly) {
      if (!(this_present_alluxioOnly && that_present_alluxioOnly))
        return false;
      if (this.alluxioOnly != that.alluxioOnly)
        return false;
    }

    boolean this_present_unchecked = true && this.isSetUnchecked();
    boolean that_present_unchecked = true && that.isSetUnchecked();
    if (this_present_unchecked || that_present_unchecked) {
      if (!(this_present_unchecked && that_present_unchecked))
        return false;
      if (this.unchecked != that.unchecked)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetRecursive()) ? 131071 : 524287);
    if (isSetRecursive())
      hashCode = hashCode * 8191 + ((recursive) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((isSetAlluxioOnly()) ? 131071 : 524287);
    if (isSetAlluxioOnly())
      hashCode = hashCode * 8191 + ((alluxioOnly) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((isSetUnchecked()) ? 131071 : 524287);
    if (isSetUnchecked())
      hashCode = hashCode * 8191 + ((unchecked) ? 131071 : 524287);

    return hashCode;
  }

  @Override
  public int compareTo(DeleteTOptions other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetRecursive()).compareTo(other.isSetRecursive());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRecursive()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.recursive, other.recursive);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetAlluxioOnly()).compareTo(other.isSetAlluxioOnly());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAlluxioOnly()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.alluxioOnly, other.alluxioOnly);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetUnchecked()).compareTo(other.isSetUnchecked());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUnchecked()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.unchecked, other.unchecked);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("DeleteTOptions(");
    boolean first = true;

    if (isSetRecursive()) {
      sb.append("recursive:");
      sb.append(this.recursive);
      first = false;
    }
    if (isSetAlluxioOnly()) {
      if (!first) sb.append(", ");
      sb.append("alluxioOnly:");
      sb.append(this.alluxioOnly);
      first = false;
    }
    if (isSetUnchecked()) {
      if (!first) sb.append(", ");
      sb.append("unchecked:");
      sb.append(this.unchecked);
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

  private static class DeleteTOptionsStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public DeleteTOptionsStandardScheme getScheme() {
      return new DeleteTOptionsStandardScheme();
    }
  }

  private static class DeleteTOptionsStandardScheme extends org.apache.thrift.scheme.StandardScheme<DeleteTOptions> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, DeleteTOptions struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // RECURSIVE
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.recursive = iprot.readBool();
              struct.setRecursiveIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ALLUXIO_ONLY
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.alluxioOnly = iprot.readBool();
              struct.setAlluxioOnlyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // UNCHECKED
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.unchecked = iprot.readBool();
              struct.setUncheckedIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, DeleteTOptions struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetRecursive()) {
        oprot.writeFieldBegin(RECURSIVE_FIELD_DESC);
        oprot.writeBool(struct.recursive);
        oprot.writeFieldEnd();
      }
      if (struct.isSetAlluxioOnly()) {
        oprot.writeFieldBegin(ALLUXIO_ONLY_FIELD_DESC);
        oprot.writeBool(struct.alluxioOnly);
        oprot.writeFieldEnd();
      }
      if (struct.isSetUnchecked()) {
        oprot.writeFieldBegin(UNCHECKED_FIELD_DESC);
        oprot.writeBool(struct.unchecked);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class DeleteTOptionsTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public DeleteTOptionsTupleScheme getScheme() {
      return new DeleteTOptionsTupleScheme();
    }
  }

  private static class DeleteTOptionsTupleScheme extends org.apache.thrift.scheme.TupleScheme<DeleteTOptions> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, DeleteTOptions struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetRecursive()) {
        optionals.set(0);
      }
      if (struct.isSetAlluxioOnly()) {
        optionals.set(1);
      }
      if (struct.isSetUnchecked()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetRecursive()) {
        oprot.writeBool(struct.recursive);
      }
      if (struct.isSetAlluxioOnly()) {
        oprot.writeBool(struct.alluxioOnly);
      }
      if (struct.isSetUnchecked()) {
        oprot.writeBool(struct.unchecked);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, DeleteTOptions struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.recursive = iprot.readBool();
        struct.setRecursiveIsSet(true);
      }
      if (incoming.get(1)) {
        struct.alluxioOnly = iprot.readBool();
        struct.setAlluxioOnlyIsSet(true);
      }
      if (incoming.get(2)) {
        struct.unchecked = iprot.readBool();
        struct.setUncheckedIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

