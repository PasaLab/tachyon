/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package alluxio.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)")
public class MountPointInfo implements org.apache.thrift.TBase<MountPointInfo, MountPointInfo._Fields>, java.io.Serializable, Cloneable, Comparable<MountPointInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MountPointInfo");

  private static final org.apache.thrift.protocol.TField UFS_URI_FIELD_DESC = new org.apache.thrift.protocol.TField("ufsUri", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField UFS_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("ufsType", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField UFS_CAPACITY_BYTES_FIELD_DESC = new org.apache.thrift.protocol.TField("ufsCapacityBytes", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField UFS_USED_BYTES_FIELD_DESC = new org.apache.thrift.protocol.TField("ufsUsedBytes", org.apache.thrift.protocol.TType.I64, (short)4);
  private static final org.apache.thrift.protocol.TField READ_ONLY_FIELD_DESC = new org.apache.thrift.protocol.TField("readOnly", org.apache.thrift.protocol.TType.BOOL, (short)5);
  private static final org.apache.thrift.protocol.TField PROPERTIES_FIELD_DESC = new org.apache.thrift.protocol.TField("properties", org.apache.thrift.protocol.TType.MAP, (short)6);
  private static final org.apache.thrift.protocol.TField SHARED_FIELD_DESC = new org.apache.thrift.protocol.TField("shared", org.apache.thrift.protocol.TType.BOOL, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new MountPointInfoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new MountPointInfoTupleSchemeFactory());
  }

  private String ufsUri; // required
  private String ufsType; // required
  private long ufsCapacityBytes; // required
  private long ufsUsedBytes; // required
  private boolean readOnly; // required
  private Map<String,String> properties; // required
  private boolean shared; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    UFS_URI((short)1, "ufsUri"),
    UFS_TYPE((short)2, "ufsType"),
    UFS_CAPACITY_BYTES((short)3, "ufsCapacityBytes"),
    UFS_USED_BYTES((short)4, "ufsUsedBytes"),
    READ_ONLY((short)5, "readOnly"),
    PROPERTIES((short)6, "properties"),
    SHARED((short)7, "shared");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // UFS_URI
          return UFS_URI;
        case 2: // UFS_TYPE
          return UFS_TYPE;
        case 3: // UFS_CAPACITY_BYTES
          return UFS_CAPACITY_BYTES;
        case 4: // UFS_USED_BYTES
          return UFS_USED_BYTES;
        case 5: // READ_ONLY
          return READ_ONLY;
        case 6: // PROPERTIES
          return PROPERTIES;
        case 7: // SHARED
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
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __UFSCAPACITYBYTES_ISSET_ID = 0;
  private static final int __UFSUSEDBYTES_ISSET_ID = 1;
  private static final int __READONLY_ISSET_ID = 2;
  private static final int __SHARED_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.UFS_URI, new org.apache.thrift.meta_data.FieldMetaData("ufsUri", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.UFS_TYPE, new org.apache.thrift.meta_data.FieldMetaData("ufsType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.UFS_CAPACITY_BYTES, new org.apache.thrift.meta_data.FieldMetaData("ufsCapacityBytes", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.UFS_USED_BYTES, new org.apache.thrift.meta_data.FieldMetaData("ufsUsedBytes", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.READ_ONLY, new org.apache.thrift.meta_data.FieldMetaData("readOnly", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.PROPERTIES, new org.apache.thrift.meta_data.FieldMetaData("properties", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.SHARED, new org.apache.thrift.meta_data.FieldMetaData("shared", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MountPointInfo.class, metaDataMap);
  }

  public MountPointInfo() {
    this.ufsCapacityBytes = -1L;

    this.ufsUsedBytes = -1L;

  }

  public MountPointInfo(
    String ufsUri,
    String ufsType,
    long ufsCapacityBytes,
    long ufsUsedBytes,
    boolean readOnly,
    Map<String,String> properties,
    boolean shared)
  {
    this();
    this.ufsUri = ufsUri;
    this.ufsType = ufsType;
    this.ufsCapacityBytes = ufsCapacityBytes;
    setUfsCapacityBytesIsSet(true);
    this.ufsUsedBytes = ufsUsedBytes;
    setUfsUsedBytesIsSet(true);
    this.readOnly = readOnly;
    setReadOnlyIsSet(true);
    this.properties = properties;
    this.shared = shared;
    setSharedIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MountPointInfo(MountPointInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetUfsUri()) {
      this.ufsUri = other.ufsUri;
    }
    if (other.isSetUfsType()) {
      this.ufsType = other.ufsType;
    }
    this.ufsCapacityBytes = other.ufsCapacityBytes;
    this.ufsUsedBytes = other.ufsUsedBytes;
    this.readOnly = other.readOnly;
    if (other.isSetProperties()) {
      Map<String,String> __this__properties = new HashMap<String,String>(other.properties);
      this.properties = __this__properties;
    }
    this.shared = other.shared;
  }

  public MountPointInfo deepCopy() {
    return new MountPointInfo(this);
  }

  @Override
  public void clear() {
    this.ufsUri = null;
    this.ufsType = null;
    this.ufsCapacityBytes = -1L;

    this.ufsUsedBytes = -1L;

    setReadOnlyIsSet(false);
    this.readOnly = false;
    this.properties = null;
    setSharedIsSet(false);
    this.shared = false;
  }

  public String getUfsUri() {
    return this.ufsUri;
  }

  public MountPointInfo setUfsUri(String ufsUri) {
    this.ufsUri = ufsUri;
    return this;
  }

  public void unsetUfsUri() {
    this.ufsUri = null;
  }

  /** Returns true if field ufsUri is set (has been assigned a value) and false otherwise */
  public boolean isSetUfsUri() {
    return this.ufsUri != null;
  }

  public void setUfsUriIsSet(boolean value) {
    if (!value) {
      this.ufsUri = null;
    }
  }

  public String getUfsType() {
    return this.ufsType;
  }

  public MountPointInfo setUfsType(String ufsType) {
    this.ufsType = ufsType;
    return this;
  }

  public void unsetUfsType() {
    this.ufsType = null;
  }

  /** Returns true if field ufsType is set (has been assigned a value) and false otherwise */
  public boolean isSetUfsType() {
    return this.ufsType != null;
  }

  public void setUfsTypeIsSet(boolean value) {
    if (!value) {
      this.ufsType = null;
    }
  }

  public long getUfsCapacityBytes() {
    return this.ufsCapacityBytes;
  }

  public MountPointInfo setUfsCapacityBytes(long ufsCapacityBytes) {
    this.ufsCapacityBytes = ufsCapacityBytes;
    setUfsCapacityBytesIsSet(true);
    return this;
  }

  public void unsetUfsCapacityBytes() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __UFSCAPACITYBYTES_ISSET_ID);
  }

  /** Returns true if field ufsCapacityBytes is set (has been assigned a value) and false otherwise */
  public boolean isSetUfsCapacityBytes() {
    return EncodingUtils.testBit(__isset_bitfield, __UFSCAPACITYBYTES_ISSET_ID);
  }

  public void setUfsCapacityBytesIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __UFSCAPACITYBYTES_ISSET_ID, value);
  }

  public long getUfsUsedBytes() {
    return this.ufsUsedBytes;
  }

  public MountPointInfo setUfsUsedBytes(long ufsUsedBytes) {
    this.ufsUsedBytes = ufsUsedBytes;
    setUfsUsedBytesIsSet(true);
    return this;
  }

  public void unsetUfsUsedBytes() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __UFSUSEDBYTES_ISSET_ID);
  }

  /** Returns true if field ufsUsedBytes is set (has been assigned a value) and false otherwise */
  public boolean isSetUfsUsedBytes() {
    return EncodingUtils.testBit(__isset_bitfield, __UFSUSEDBYTES_ISSET_ID);
  }

  public void setUfsUsedBytesIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __UFSUSEDBYTES_ISSET_ID, value);
  }

  public boolean isReadOnly() {
    return this.readOnly;
  }

  public MountPointInfo setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
    setReadOnlyIsSet(true);
    return this;
  }

  public void unsetReadOnly() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __READONLY_ISSET_ID);
  }

  /** Returns true if field readOnly is set (has been assigned a value) and false otherwise */
  public boolean isSetReadOnly() {
    return EncodingUtils.testBit(__isset_bitfield, __READONLY_ISSET_ID);
  }

  public void setReadOnlyIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __READONLY_ISSET_ID, value);
  }

  public int getPropertiesSize() {
    return (this.properties == null) ? 0 : this.properties.size();
  }

  public void putToProperties(String key, String val) {
    if (this.properties == null) {
      this.properties = new HashMap<String,String>();
    }
    this.properties.put(key, val);
  }

  public Map<String,String> getProperties() {
    return this.properties;
  }

  public MountPointInfo setProperties(Map<String,String> properties) {
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

  public MountPointInfo setShared(boolean shared) {
    this.shared = shared;
    setSharedIsSet(true);
    return this;
  }

  public void unsetShared() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SHARED_ISSET_ID);
  }

  /** Returns true if field shared is set (has been assigned a value) and false otherwise */
  public boolean isSetShared() {
    return EncodingUtils.testBit(__isset_bitfield, __SHARED_ISSET_ID);
  }

  public void setSharedIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SHARED_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case UFS_URI:
      if (value == null) {
        unsetUfsUri();
      } else {
        setUfsUri((String)value);
      }
      break;

    case UFS_TYPE:
      if (value == null) {
        unsetUfsType();
      } else {
        setUfsType((String)value);
      }
      break;

    case UFS_CAPACITY_BYTES:
      if (value == null) {
        unsetUfsCapacityBytes();
      } else {
        setUfsCapacityBytes((Long)value);
      }
      break;

    case UFS_USED_BYTES:
      if (value == null) {
        unsetUfsUsedBytes();
      } else {
        setUfsUsedBytes((Long)value);
      }
      break;

    case READ_ONLY:
      if (value == null) {
        unsetReadOnly();
      } else {
        setReadOnly((Boolean)value);
      }
      break;

    case PROPERTIES:
      if (value == null) {
        unsetProperties();
      } else {
        setProperties((Map<String,String>)value);
      }
      break;

    case SHARED:
      if (value == null) {
        unsetShared();
      } else {
        setShared((Boolean)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case UFS_URI:
      return getUfsUri();

    case UFS_TYPE:
      return getUfsType();

    case UFS_CAPACITY_BYTES:
      return getUfsCapacityBytes();

    case UFS_USED_BYTES:
      return getUfsUsedBytes();

    case READ_ONLY:
      return isReadOnly();

    case PROPERTIES:
      return getProperties();

    case SHARED:
      return isShared();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case UFS_URI:
      return isSetUfsUri();
    case UFS_TYPE:
      return isSetUfsType();
    case UFS_CAPACITY_BYTES:
      return isSetUfsCapacityBytes();
    case UFS_USED_BYTES:
      return isSetUfsUsedBytes();
    case READ_ONLY:
      return isSetReadOnly();
    case PROPERTIES:
      return isSetProperties();
    case SHARED:
      return isSetShared();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof MountPointInfo)
      return this.equals((MountPointInfo)that);
    return false;
  }

  public boolean equals(MountPointInfo that) {
    if (that == null)
      return false;

    boolean this_present_ufsUri = true && this.isSetUfsUri();
    boolean that_present_ufsUri = true && that.isSetUfsUri();
    if (this_present_ufsUri || that_present_ufsUri) {
      if (!(this_present_ufsUri && that_present_ufsUri))
        return false;
      if (!this.ufsUri.equals(that.ufsUri))
        return false;
    }

    boolean this_present_ufsType = true && this.isSetUfsType();
    boolean that_present_ufsType = true && that.isSetUfsType();
    if (this_present_ufsType || that_present_ufsType) {
      if (!(this_present_ufsType && that_present_ufsType))
        return false;
      if (!this.ufsType.equals(that.ufsType))
        return false;
    }

    boolean this_present_ufsCapacityBytes = true;
    boolean that_present_ufsCapacityBytes = true;
    if (this_present_ufsCapacityBytes || that_present_ufsCapacityBytes) {
      if (!(this_present_ufsCapacityBytes && that_present_ufsCapacityBytes))
        return false;
      if (this.ufsCapacityBytes != that.ufsCapacityBytes)
        return false;
    }

    boolean this_present_ufsUsedBytes = true;
    boolean that_present_ufsUsedBytes = true;
    if (this_present_ufsUsedBytes || that_present_ufsUsedBytes) {
      if (!(this_present_ufsUsedBytes && that_present_ufsUsedBytes))
        return false;
      if (this.ufsUsedBytes != that.ufsUsedBytes)
        return false;
    }

    boolean this_present_readOnly = true;
    boolean that_present_readOnly = true;
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

    boolean this_present_shared = true;
    boolean that_present_shared = true;
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
    List<Object> list = new ArrayList<Object>();

    boolean present_ufsUri = true && (isSetUfsUri());
    list.add(present_ufsUri);
    if (present_ufsUri)
      list.add(ufsUri);

    boolean present_ufsType = true && (isSetUfsType());
    list.add(present_ufsType);
    if (present_ufsType)
      list.add(ufsType);

    boolean present_ufsCapacityBytes = true;
    list.add(present_ufsCapacityBytes);
    if (present_ufsCapacityBytes)
      list.add(ufsCapacityBytes);

    boolean present_ufsUsedBytes = true;
    list.add(present_ufsUsedBytes);
    if (present_ufsUsedBytes)
      list.add(ufsUsedBytes);

    boolean present_readOnly = true;
    list.add(present_readOnly);
    if (present_readOnly)
      list.add(readOnly);

    boolean present_properties = true && (isSetProperties());
    list.add(present_properties);
    if (present_properties)
      list.add(properties);

    boolean present_shared = true;
    list.add(present_shared);
    if (present_shared)
      list.add(shared);

    return list.hashCode();
  }

  @Override
  public int compareTo(MountPointInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetUfsUri()).compareTo(other.isSetUfsUri());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUfsUri()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ufsUri, other.ufsUri);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUfsType()).compareTo(other.isSetUfsType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUfsType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ufsType, other.ufsType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUfsCapacityBytes()).compareTo(other.isSetUfsCapacityBytes());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUfsCapacityBytes()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ufsCapacityBytes, other.ufsCapacityBytes);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUfsUsedBytes()).compareTo(other.isSetUfsUsedBytes());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUfsUsedBytes()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ufsUsedBytes, other.ufsUsedBytes);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetReadOnly()).compareTo(other.isSetReadOnly());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReadOnly()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.readOnly, other.readOnly);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProperties()).compareTo(other.isSetProperties());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProperties()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.properties, other.properties);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetShared()).compareTo(other.isSetShared());
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
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("MountPointInfo(");
    boolean first = true;

    sb.append("ufsUri:");
    if (this.ufsUri == null) {
      sb.append("null");
    } else {
      sb.append(this.ufsUri);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("ufsType:");
    if (this.ufsType == null) {
      sb.append("null");
    } else {
      sb.append(this.ufsType);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("ufsCapacityBytes:");
    sb.append(this.ufsCapacityBytes);
    first = false;
    if (!first) sb.append(", ");
    sb.append("ufsUsedBytes:");
    sb.append(this.ufsUsedBytes);
    first = false;
    if (!first) sb.append(", ");
    sb.append("readOnly:");
    sb.append(this.readOnly);
    first = false;
    if (!first) sb.append(", ");
    sb.append("properties:");
    if (this.properties == null) {
      sb.append("null");
    } else {
      sb.append(this.properties);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("shared:");
    sb.append(this.shared);
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

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class MountPointInfoStandardSchemeFactory implements SchemeFactory {
    public MountPointInfoStandardScheme getScheme() {
      return new MountPointInfoStandardScheme();
    }
  }

  private static class MountPointInfoStandardScheme extends StandardScheme<MountPointInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, MountPointInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // UFS_URI
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.ufsUri = iprot.readString();
              struct.setUfsUriIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // UFS_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.ufsType = iprot.readString();
              struct.setUfsTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // UFS_CAPACITY_BYTES
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.ufsCapacityBytes = iprot.readI64();
              struct.setUfsCapacityBytesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // UFS_USED_BYTES
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.ufsUsedBytes = iprot.readI64();
              struct.setUfsUsedBytesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // READ_ONLY
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.readOnly = iprot.readBool();
              struct.setReadOnlyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // PROPERTIES
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map68 = iprot.readMapBegin();
                struct.properties = new HashMap<String,String>(2*_map68.size);
                String _key69;
                String _val70;
                for (int _i71 = 0; _i71 < _map68.size; ++_i71)
                {
                  _key69 = iprot.readString();
                  _val70 = iprot.readString();
                  struct.properties.put(_key69, _val70);
                }
                iprot.readMapEnd();
              }
              struct.setPropertiesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // SHARED
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, MountPointInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.ufsUri != null) {
        oprot.writeFieldBegin(UFS_URI_FIELD_DESC);
        oprot.writeString(struct.ufsUri);
        oprot.writeFieldEnd();
      }
      if (struct.ufsType != null) {
        oprot.writeFieldBegin(UFS_TYPE_FIELD_DESC);
        oprot.writeString(struct.ufsType);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(UFS_CAPACITY_BYTES_FIELD_DESC);
      oprot.writeI64(struct.ufsCapacityBytes);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(UFS_USED_BYTES_FIELD_DESC);
      oprot.writeI64(struct.ufsUsedBytes);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(READ_ONLY_FIELD_DESC);
      oprot.writeBool(struct.readOnly);
      oprot.writeFieldEnd();
      if (struct.properties != null) {
        oprot.writeFieldBegin(PROPERTIES_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, struct.properties.size()));
          for (Map.Entry<String, String> _iter72 : struct.properties.entrySet())
          {
            oprot.writeString(_iter72.getKey());
            oprot.writeString(_iter72.getValue());
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(SHARED_FIELD_DESC);
      oprot.writeBool(struct.shared);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MountPointInfoTupleSchemeFactory implements SchemeFactory {
    public MountPointInfoTupleScheme getScheme() {
      return new MountPointInfoTupleScheme();
    }
  }

  private static class MountPointInfoTupleScheme extends TupleScheme<MountPointInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MountPointInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetUfsUri()) {
        optionals.set(0);
      }
      if (struct.isSetUfsType()) {
        optionals.set(1);
      }
      if (struct.isSetUfsCapacityBytes()) {
        optionals.set(2);
      }
      if (struct.isSetUfsUsedBytes()) {
        optionals.set(3);
      }
      if (struct.isSetReadOnly()) {
        optionals.set(4);
      }
      if (struct.isSetProperties()) {
        optionals.set(5);
      }
      if (struct.isSetShared()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetUfsUri()) {
        oprot.writeString(struct.ufsUri);
      }
      if (struct.isSetUfsType()) {
        oprot.writeString(struct.ufsType);
      }
      if (struct.isSetUfsCapacityBytes()) {
        oprot.writeI64(struct.ufsCapacityBytes);
      }
      if (struct.isSetUfsUsedBytes()) {
        oprot.writeI64(struct.ufsUsedBytes);
      }
      if (struct.isSetReadOnly()) {
        oprot.writeBool(struct.readOnly);
      }
      if (struct.isSetProperties()) {
        {
          oprot.writeI32(struct.properties.size());
          for (Map.Entry<String, String> _iter73 : struct.properties.entrySet())
          {
            oprot.writeString(_iter73.getKey());
            oprot.writeString(_iter73.getValue());
          }
        }
      }
      if (struct.isSetShared()) {
        oprot.writeBool(struct.shared);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MountPointInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.ufsUri = iprot.readString();
        struct.setUfsUriIsSet(true);
      }
      if (incoming.get(1)) {
        struct.ufsType = iprot.readString();
        struct.setUfsTypeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.ufsCapacityBytes = iprot.readI64();
        struct.setUfsCapacityBytesIsSet(true);
      }
      if (incoming.get(3)) {
        struct.ufsUsedBytes = iprot.readI64();
        struct.setUfsUsedBytesIsSet(true);
      }
      if (incoming.get(4)) {
        struct.readOnly = iprot.readBool();
        struct.setReadOnlyIsSet(true);
      }
      if (incoming.get(5)) {
        {
          org.apache.thrift.protocol.TMap _map74 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.properties = new HashMap<String,String>(2*_map74.size);
          String _key75;
          String _val76;
          for (int _i77 = 0; _i77 < _map74.size; ++_i77)
          {
            _key75 = iprot.readString();
            _val76 = iprot.readString();
            struct.properties.put(_key75, _val76);
          }
        }
        struct.setPropertiesIsSet(true);
      }
      if (incoming.get(6)) {
        struct.shared = iprot.readBool();
        struct.setSharedIsSet(true);
      }
    }
  }

}

