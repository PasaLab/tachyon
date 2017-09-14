/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package alluxio.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-09-14")
public class getUserWorkersTResponse implements org.apache.thrift.TBase<getUserWorkersTResponse, getUserWorkersTResponse._Fields>, java.io.Serializable, Cloneable, Comparable<getUserWorkersTResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("getUserWorkersTResponse");

  private static final org.apache.thrift.protocol.TField WORKER_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("workerList", org.apache.thrift.protocol.TType.LIST, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new getUserWorkersTResponseStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new getUserWorkersTResponseTupleSchemeFactory();

  public java.util.List<alluxio.thrift.WorkerNetAddress> workerList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    WORKER_LIST((short)1, "workerList");

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
        case 1: // WORKER_LIST
          return WORKER_LIST;
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
    tmpMap.put(_Fields.WORKER_LIST, new org.apache.thrift.meta_data.FieldMetaData("workerList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, alluxio.thrift.WorkerNetAddress.class))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(getUserWorkersTResponse.class, metaDataMap);
  }

  public getUserWorkersTResponse() {
  }

  public getUserWorkersTResponse(
    java.util.List<alluxio.thrift.WorkerNetAddress> workerList)
  {
    this();
    this.workerList = workerList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public getUserWorkersTResponse(getUserWorkersTResponse other) {
    if (other.isSetWorkerList()) {
      java.util.List<alluxio.thrift.WorkerNetAddress> __this__workerList = new java.util.ArrayList<alluxio.thrift.WorkerNetAddress>(other.workerList.size());
      for (alluxio.thrift.WorkerNetAddress other_element : other.workerList) {
        __this__workerList.add(new alluxio.thrift.WorkerNetAddress(other_element));
      }
      this.workerList = __this__workerList;
    }
  }

  public getUserWorkersTResponse deepCopy() {
    return new getUserWorkersTResponse(this);
  }

  @Override
  public void clear() {
    this.workerList = null;
  }

  public int getWorkerListSize() {
    return (this.workerList == null) ? 0 : this.workerList.size();
  }

  public java.util.Iterator<alluxio.thrift.WorkerNetAddress> getWorkerListIterator() {
    return (this.workerList == null) ? null : this.workerList.iterator();
  }

  public void addToWorkerList(alluxio.thrift.WorkerNetAddress elem) {
    if (this.workerList == null) {
      this.workerList = new java.util.ArrayList<alluxio.thrift.WorkerNetAddress>();
    }
    this.workerList.add(elem);
  }

  public java.util.List<alluxio.thrift.WorkerNetAddress> getWorkerList() {
    return this.workerList;
  }

  public getUserWorkersTResponse setWorkerList(java.util.List<alluxio.thrift.WorkerNetAddress> workerList) {
    this.workerList = workerList;
    return this;
  }

  public void unsetWorkerList() {
    this.workerList = null;
  }

  /** Returns true if field workerList is set (has been assigned a value) and false otherwise */
  public boolean isSetWorkerList() {
    return this.workerList != null;
  }

  public void setWorkerListIsSet(boolean value) {
    if (!value) {
      this.workerList = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case WORKER_LIST:
      if (value == null) {
        unsetWorkerList();
      } else {
        setWorkerList((java.util.List<alluxio.thrift.WorkerNetAddress>)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case WORKER_LIST:
      return getWorkerList();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case WORKER_LIST:
      return isSetWorkerList();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof getUserWorkersTResponse)
      return this.equals((getUserWorkersTResponse)that);
    return false;
  }

  public boolean equals(getUserWorkersTResponse that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_workerList = true && this.isSetWorkerList();
    boolean that_present_workerList = true && that.isSetWorkerList();
    if (this_present_workerList || that_present_workerList) {
      if (!(this_present_workerList && that_present_workerList))
        return false;
      if (!this.workerList.equals(that.workerList))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetWorkerList()) ? 131071 : 524287);
    if (isSetWorkerList())
      hashCode = hashCode * 8191 + workerList.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(getUserWorkersTResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetWorkerList()).compareTo(other.isSetWorkerList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetWorkerList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.workerList, other.workerList);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("getUserWorkersTResponse(");
    boolean first = true;

    sb.append("workerList:");
    if (this.workerList == null) {
      sb.append("null");
    } else {
      sb.append(this.workerList);
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

  private static class getUserWorkersTResponseStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public getUserWorkersTResponseStandardScheme getScheme() {
      return new getUserWorkersTResponseStandardScheme();
    }
  }

  private static class getUserWorkersTResponseStandardScheme extends org.apache.thrift.scheme.StandardScheme<getUserWorkersTResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, getUserWorkersTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // WORKER_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list16 = iprot.readListBegin();
                struct.workerList = new java.util.ArrayList<alluxio.thrift.WorkerNetAddress>(_list16.size);
                alluxio.thrift.WorkerNetAddress _elem17;
                for (int _i18 = 0; _i18 < _list16.size; ++_i18)
                {
                  _elem17 = new alluxio.thrift.WorkerNetAddress();
                  _elem17.read(iprot);
                  struct.workerList.add(_elem17);
                }
                iprot.readListEnd();
              }
              struct.setWorkerListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, getUserWorkersTResponse struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.workerList != null) {
        oprot.writeFieldBegin(WORKER_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.workerList.size()));
          for (alluxio.thrift.WorkerNetAddress _iter19 : struct.workerList)
          {
            _iter19.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class getUserWorkersTResponseTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public getUserWorkersTResponseTupleScheme getScheme() {
      return new getUserWorkersTResponseTupleScheme();
    }
  }

  private static class getUserWorkersTResponseTupleScheme extends org.apache.thrift.scheme.TupleScheme<getUserWorkersTResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, getUserWorkersTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetWorkerList()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetWorkerList()) {
        {
          oprot.writeI32(struct.workerList.size());
          for (alluxio.thrift.WorkerNetAddress _iter20 : struct.workerList)
          {
            _iter20.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, getUserWorkersTResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list21 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.workerList = new java.util.ArrayList<alluxio.thrift.WorkerNetAddress>(_list21.size);
          alluxio.thrift.WorkerNetAddress _elem22;
          for (int _i23 = 0; _i23 < _list21.size; ++_i23)
          {
            _elem22 = new alluxio.thrift.WorkerNetAddress();
            _elem22.read(iprot);
            struct.workerList.add(_elem22);
          }
        }
        struct.setWorkerListIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

