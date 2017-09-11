/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package alluxio.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-09-11")
public class PromoteBlockTResponse implements org.apache.thrift.TBase<PromoteBlockTResponse, PromoteBlockTResponse._Fields>, java.io.Serializable, Cloneable, Comparable<PromoteBlockTResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PromoteBlockTResponse");

  private static final org.apache.thrift.protocol.TField PROMOTED_FIELD_DESC = new org.apache.thrift.protocol.TField("promoted", org.apache.thrift.protocol.TType.BOOL, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new PromoteBlockTResponseStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new PromoteBlockTResponseTupleSchemeFactory();

  public boolean promoted; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PROMOTED((short)1, "promoted");

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
        case 1: // PROMOTED
          return PROMOTED;
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
  private static final int __PROMOTED_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PROMOTED, new org.apache.thrift.meta_data.FieldMetaData("promoted", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PromoteBlockTResponse.class, metaDataMap);
  }

  public PromoteBlockTResponse() {
  }

  public PromoteBlockTResponse(
    boolean promoted)
  {
    this();
    this.promoted = promoted;
    setPromotedIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PromoteBlockTResponse(PromoteBlockTResponse other) {
    __isset_bitfield = other.__isset_bitfield;
    this.promoted = other.promoted;
  }

  public PromoteBlockTResponse deepCopy() {
    return new PromoteBlockTResponse(this);
  }

  @Override
  public void clear() {
    setPromotedIsSet(false);
    this.promoted = false;
  }

  public boolean isPromoted() {
    return this.promoted;
  }

  public PromoteBlockTResponse setPromoted(boolean promoted) {
    this.promoted = promoted;
    setPromotedIsSet(true);
    return this;
  }

  public void unsetPromoted() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __PROMOTED_ISSET_ID);
  }

  /** Returns true if field promoted is set (has been assigned a value) and false otherwise */
  public boolean isSetPromoted() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __PROMOTED_ISSET_ID);
  }

  public void setPromotedIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __PROMOTED_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case PROMOTED:
      if (value == null) {
        unsetPromoted();
      } else {
        setPromoted((java.lang.Boolean)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case PROMOTED:
      return isPromoted();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case PROMOTED:
      return isSetPromoted();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof PromoteBlockTResponse)
      return this.equals((PromoteBlockTResponse)that);
    return false;
  }

  public boolean equals(PromoteBlockTResponse that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_promoted = true;
    boolean that_present_promoted = true;
    if (this_present_promoted || that_present_promoted) {
      if (!(this_present_promoted && that_present_promoted))
        return false;
      if (this.promoted != that.promoted)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((promoted) ? 131071 : 524287);

    return hashCode;
  }

  @Override
  public int compareTo(PromoteBlockTResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetPromoted()).compareTo(other.isSetPromoted());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPromoted()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.promoted, other.promoted);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("PromoteBlockTResponse(");
    boolean first = true;

    sb.append("promoted:");
    sb.append(this.promoted);
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

  private static class PromoteBlockTResponseStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public PromoteBlockTResponseStandardScheme getScheme() {
      return new PromoteBlockTResponseStandardScheme();
    }
  }

  private static class PromoteBlockTResponseStandardScheme extends org.apache.thrift.scheme.StandardScheme<PromoteBlockTResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, PromoteBlockTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PROMOTED
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.promoted = iprot.readBool();
              struct.setPromotedIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, PromoteBlockTResponse struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PROMOTED_FIELD_DESC);
      oprot.writeBool(struct.promoted);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PromoteBlockTResponseTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public PromoteBlockTResponseTupleScheme getScheme() {
      return new PromoteBlockTResponseTupleScheme();
    }
  }

  private static class PromoteBlockTResponseTupleScheme extends org.apache.thrift.scheme.TupleScheme<PromoteBlockTResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PromoteBlockTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetPromoted()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetPromoted()) {
        oprot.writeBool(struct.promoted);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PromoteBlockTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.promoted = iprot.readBool();
        struct.setPromotedIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

