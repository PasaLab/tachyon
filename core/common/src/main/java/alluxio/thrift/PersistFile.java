/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package alluxio.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-09-08")
public class PersistFile implements org.apache.thrift.TBase<PersistFile, PersistFile._Fields>, java.io.Serializable, Cloneable, Comparable<PersistFile> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PersistFile");

  private static final org.apache.thrift.protocol.TField FILE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("fileId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField BLOCK_IDS_FIELD_DESC = new org.apache.thrift.protocol.TField("blockIds", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new PersistFileStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new PersistFileTupleSchemeFactory();

  public long fileId; // required
  public java.util.List<java.lang.Long> blockIds; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    FILE_ID((short)1, "fileId"),
    BLOCK_IDS((short)2, "blockIds");

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
        case 1: // FILE_ID
          return FILE_ID;
        case 2: // BLOCK_IDS
          return BLOCK_IDS;
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
  private static final int __FILEID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.FILE_ID, new org.apache.thrift.meta_data.FieldMetaData("fileId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.BLOCK_IDS, new org.apache.thrift.meta_data.FieldMetaData("blockIds", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PersistFile.class, metaDataMap);
  }

  public PersistFile() {
  }

  public PersistFile(
    long fileId,
    java.util.List<java.lang.Long> blockIds)
  {
    this();
    this.fileId = fileId;
    setFileIdIsSet(true);
    this.blockIds = blockIds;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PersistFile(PersistFile other) {
    __isset_bitfield = other.__isset_bitfield;
    this.fileId = other.fileId;
    if (other.isSetBlockIds()) {
      java.util.List<java.lang.Long> __this__blockIds = new java.util.ArrayList<java.lang.Long>(other.blockIds);
      this.blockIds = __this__blockIds;
    }
  }

  public PersistFile deepCopy() {
    return new PersistFile(this);
  }

  @Override
  public void clear() {
    setFileIdIsSet(false);
    this.fileId = 0;
    this.blockIds = null;
  }

  public long getFileId() {
    return this.fileId;
  }

  public PersistFile setFileId(long fileId) {
    this.fileId = fileId;
    setFileIdIsSet(true);
    return this;
  }

  public void unsetFileId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __FILEID_ISSET_ID);
  }

  /** Returns true if field fileId is set (has been assigned a value) and false otherwise */
  public boolean isSetFileId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __FILEID_ISSET_ID);
  }

  public void setFileIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __FILEID_ISSET_ID, value);
  }

  public int getBlockIdsSize() {
    return (this.blockIds == null) ? 0 : this.blockIds.size();
  }

  public java.util.Iterator<java.lang.Long> getBlockIdsIterator() {
    return (this.blockIds == null) ? null : this.blockIds.iterator();
  }

  public void addToBlockIds(long elem) {
    if (this.blockIds == null) {
      this.blockIds = new java.util.ArrayList<java.lang.Long>();
    }
    this.blockIds.add(elem);
  }

  public java.util.List<java.lang.Long> getBlockIds() {
    return this.blockIds;
  }

  public PersistFile setBlockIds(java.util.List<java.lang.Long> blockIds) {
    this.blockIds = blockIds;
    return this;
  }

  public void unsetBlockIds() {
    this.blockIds = null;
  }

  /** Returns true if field blockIds is set (has been assigned a value) and false otherwise */
  public boolean isSetBlockIds() {
    return this.blockIds != null;
  }

  public void setBlockIdsIsSet(boolean value) {
    if (!value) {
      this.blockIds = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case FILE_ID:
      if (value == null) {
        unsetFileId();
      } else {
        setFileId((java.lang.Long)value);
      }
      break;

    case BLOCK_IDS:
      if (value == null) {
        unsetBlockIds();
      } else {
        setBlockIds((java.util.List<java.lang.Long>)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case FILE_ID:
      return getFileId();

    case BLOCK_IDS:
      return getBlockIds();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case FILE_ID:
      return isSetFileId();
    case BLOCK_IDS:
      return isSetBlockIds();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof PersistFile)
      return this.equals((PersistFile)that);
    return false;
  }

  public boolean equals(PersistFile that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_fileId = true;
    boolean that_present_fileId = true;
    if (this_present_fileId || that_present_fileId) {
      if (!(this_present_fileId && that_present_fileId))
        return false;
      if (this.fileId != that.fileId)
        return false;
    }

    boolean this_present_blockIds = true && this.isSetBlockIds();
    boolean that_present_blockIds = true && that.isSetBlockIds();
    if (this_present_blockIds || that_present_blockIds) {
      if (!(this_present_blockIds && that_present_blockIds))
        return false;
      if (!this.blockIds.equals(that.blockIds))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(fileId);

    hashCode = hashCode * 8191 + ((isSetBlockIds()) ? 131071 : 524287);
    if (isSetBlockIds())
      hashCode = hashCode * 8191 + blockIds.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(PersistFile other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetFileId()).compareTo(other.isSetFileId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFileId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fileId, other.fileId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetBlockIds()).compareTo(other.isSetBlockIds());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBlockIds()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.blockIds, other.blockIds);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("PersistFile(");
    boolean first = true;

    sb.append("fileId:");
    sb.append(this.fileId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("blockIds:");
    if (this.blockIds == null) {
      sb.append("null");
    } else {
      sb.append(this.blockIds);
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class PersistFileStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public PersistFileStandardScheme getScheme() {
      return new PersistFileStandardScheme();
    }
  }

  private static class PersistFileStandardScheme extends org.apache.thrift.scheme.StandardScheme<PersistFile> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, PersistFile struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // FILE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.fileId = iprot.readI64();
              struct.setFileIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // BLOCK_IDS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list94 = iprot.readListBegin();
                struct.blockIds = new java.util.ArrayList<java.lang.Long>(_list94.size);
                long _elem95;
                for (int _i96 = 0; _i96 < _list94.size; ++_i96)
                {
                  _elem95 = iprot.readI64();
                  struct.blockIds.add(_elem95);
                }
                iprot.readListEnd();
              }
              struct.setBlockIdsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, PersistFile struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(FILE_ID_FIELD_DESC);
      oprot.writeI64(struct.fileId);
      oprot.writeFieldEnd();
      if (struct.blockIds != null) {
        oprot.writeFieldBegin(BLOCK_IDS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I64, struct.blockIds.size()));
          for (long _iter97 : struct.blockIds)
          {
            oprot.writeI64(_iter97);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PersistFileTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public PersistFileTupleScheme getScheme() {
      return new PersistFileTupleScheme();
    }
  }

  private static class PersistFileTupleScheme extends org.apache.thrift.scheme.TupleScheme<PersistFile> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PersistFile struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetFileId()) {
        optionals.set(0);
      }
      if (struct.isSetBlockIds()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetFileId()) {
        oprot.writeI64(struct.fileId);
      }
      if (struct.isSetBlockIds()) {
        {
          oprot.writeI32(struct.blockIds.size());
          for (long _iter98 : struct.blockIds)
          {
            oprot.writeI64(_iter98);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PersistFile struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.fileId = iprot.readI64();
        struct.setFileIdIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list99 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I64, iprot.readI32());
          struct.blockIds = new java.util.ArrayList<java.lang.Long>(_list99.size);
          long _elem100;
          for (int _i101 = 0; _i101 < _list99.size; ++_i101)
          {
            _elem100 = iprot.readI64();
            struct.blockIds.add(_elem100);
          }
        }
        struct.setBlockIdsIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

