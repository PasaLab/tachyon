// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: metedataService.proto

package alluxio.client.file.cache.remote.grpc.service;

/**
 * Protobuf type {@code proto.UpdateInfoList}
 */
public  final class UpdateInfoList extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:proto.UpdateInfoList)
    UpdateInfoListOrBuilder {
private static final long serialVersionUID = 0L;
  // Use UpdateInfoList.newBuilder() to construct.
  private UpdateInfoList(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private UpdateInfoList() {
    infos_ = java.util.Collections.emptyList();
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private UpdateInfoList(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
              infos_ = new java.util.ArrayList<UpdateInfo>();
              mutable_bitField0_ |= 0x00000001;
            }
            infos_.add(
                input.readMessage(UpdateInfo.parser(), extensionRegistry));
            break;
          }
          default: {
            if (!parseUnknownFieldProto3(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
        infos_ = java.util.Collections.unmodifiableList(infos_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return MetedataServiceManager.internal_static_proto_UpdateInfoList_descriptor;
  }

  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return MetedataServiceManager.internal_static_proto_UpdateInfoList_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            UpdateInfoList.class, Builder.class);
  }

  public static final int INFOS_FIELD_NUMBER = 1;
  private java.util.List<UpdateInfo> infos_;
  /**
   * <code>repeated .proto.UpdateInfo infos = 1;</code>
   */
  public java.util.List<UpdateInfo> getInfosList() {
    return infos_;
  }
  /**
   * <code>repeated .proto.UpdateInfo infos = 1;</code>
   */
  public java.util.List<? extends UpdateInfoOrBuilder>
      getInfosOrBuilderList() {
    return infos_;
  }
  /**
   * <code>repeated .proto.UpdateInfo infos = 1;</code>
   */
  public int getInfosCount() {
    return infos_.size();
  }
  /**
   * <code>repeated .proto.UpdateInfo infos = 1;</code>
   */
  public UpdateInfo getInfos(int index) {
    return infos_.get(index);
  }
  /**
   * <code>repeated .proto.UpdateInfo infos = 1;</code>
   */
  public UpdateInfoOrBuilder getInfosOrBuilder(
      int index) {
    return infos_.get(index);
  }

  private byte memoizedIsInitialized = -1;
  @Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    for (int i = 0; i < infos_.size(); i++) {
      output.writeMessage(1, infos_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < infos_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, infos_.get(i));
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof UpdateInfoList)) {
      return super.equals(obj);
    }
    UpdateInfoList other = (UpdateInfoList) obj;

    boolean result = true;
    result = result && getInfosList()
        .equals(other.getInfosList());
    result = result && unknownFields.equals(other.unknownFields);
    return result;
  }

  @Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (getInfosCount() > 0) {
      hash = (37 * hash) + INFOS_FIELD_NUMBER;
      hash = (53 * hash) + getInfosList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static UpdateInfoList parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static UpdateInfoList parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static UpdateInfoList parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static UpdateInfoList parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static UpdateInfoList parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static UpdateInfoList parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static UpdateInfoList parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static UpdateInfoList parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static UpdateInfoList parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static UpdateInfoList parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static UpdateInfoList parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static UpdateInfoList parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(UpdateInfoList prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @Override
  protected Builder newBuilderForType(
      BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code proto.UpdateInfoList}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:proto.UpdateInfoList)
      UpdateInfoListOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return MetedataServiceManager.internal_static_proto_UpdateInfoList_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return MetedataServiceManager.internal_static_proto_UpdateInfoList_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              UpdateInfoList.class, Builder.class);
    }

    // Construct using alluxio.client.file.cache.remote.net.service.UpdateInfoList.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
        getInfosFieldBuilder();
      }
    }
    @Override
    public Builder clear() {
      super.clear();
      if (infosBuilder_ == null) {
        infos_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        infosBuilder_.clear();
      }
      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return MetedataServiceManager.internal_static_proto_UpdateInfoList_descriptor;
    }

    @Override
    public UpdateInfoList getDefaultInstanceForType() {
      return UpdateInfoList.getDefaultInstance();
    }

    @Override
    public UpdateInfoList build() {
      UpdateInfoList result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public UpdateInfoList buildPartial() {
      UpdateInfoList result = new UpdateInfoList(this);
      int from_bitField0_ = bitField0_;
      if (infosBuilder_ == null) {
        if (((bitField0_ & 0x00000001) == 0x00000001)) {
          infos_ = java.util.Collections.unmodifiableList(infos_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.infos_ = infos_;
      } else {
        result.infos_ = infosBuilder_.build();
      }
      onBuilt();
      return result;
    }

    @Override
    public Builder clone() {
      return (Builder) super.clone();
    }
    @Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    @Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    @Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    @Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    @Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    @Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof UpdateInfoList) {
        return mergeFrom((UpdateInfoList)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(UpdateInfoList other) {
      if (other == UpdateInfoList.getDefaultInstance()) return this;
      if (infosBuilder_ == null) {
        if (!other.infos_.isEmpty()) {
          if (infos_.isEmpty()) {
            infos_ = other.infos_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureInfosIsMutable();
            infos_.addAll(other.infos_);
          }
          onChanged();
        }
      } else {
        if (!other.infos_.isEmpty()) {
          if (infosBuilder_.isEmpty()) {
            infosBuilder_.dispose();
            infosBuilder_ = null;
            infos_ = other.infos_;
            bitField0_ = (bitField0_ & ~0x00000001);
            infosBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getInfosFieldBuilder() : null;
          } else {
            infosBuilder_.addAllMessages(other.infos_);
          }
        }
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @Override
    public final boolean isInitialized() {
      return true;
    }

    @Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      UpdateInfoList parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (UpdateInfoList) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<UpdateInfo> infos_ =
      java.util.Collections.emptyList();
    private void ensureInfosIsMutable() {
      if (!((bitField0_ & 0x00000001) == 0x00000001)) {
        infos_ = new java.util.ArrayList<UpdateInfo>(infos_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        UpdateInfo, UpdateInfo.Builder, UpdateInfoOrBuilder> infosBuilder_;

    /**
     * <code>repeated .proto.UpdateInfo infos = 1;</code>
     */
    public java.util.List<UpdateInfo> getInfosList() {
      if (infosBuilder_ == null) {
        return java.util.Collections.unmodifiableList(infos_);
      } else {
        return infosBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .proto.UpdateInfo infos = 1;</code>
     */
    public int getInfosCount() {
      if (infosBuilder_ == null) {
        return infos_.size();
      } else {
        return infosBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .proto.UpdateInfo infos = 1;</code>
     */
    public UpdateInfo getInfos(int index) {
      if (infosBuilder_ == null) {
        return infos_.get(index);
      } else {
        return infosBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .proto.UpdateInfo infos = 1;</code>
     */
    public Builder setInfos(
        int index, UpdateInfo value) {
      if (infosBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureInfosIsMutable();
        infos_.set(index, value);
        onChanged();
      } else {
        infosBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .proto.UpdateInfo infos = 1;</code>
     */
    public Builder setInfos(
        int index, UpdateInfo.Builder builderForValue) {
      if (infosBuilder_ == null) {
        ensureInfosIsMutable();
        infos_.set(index, builderForValue.build());
        onChanged();
      } else {
        infosBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .proto.UpdateInfo infos = 1;</code>
     */
    public Builder addInfos(UpdateInfo value) {
      if (infosBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureInfosIsMutable();
        infos_.add(value);
        onChanged();
      } else {
        infosBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .proto.UpdateInfo infos = 1;</code>
     */
    public Builder addInfos(
        int index, UpdateInfo value) {
      if (infosBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureInfosIsMutable();
        infos_.add(index, value);
        onChanged();
      } else {
        infosBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .proto.UpdateInfo infos = 1;</code>
     */
    public Builder addInfos(
        UpdateInfo.Builder builderForValue) {
      if (infosBuilder_ == null) {
        ensureInfosIsMutable();
        infos_.add(builderForValue.build());
        onChanged();
      } else {
        infosBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .proto.UpdateInfo infos = 1;</code>
     */
    public Builder addInfos(
        int index, UpdateInfo.Builder builderForValue) {
      if (infosBuilder_ == null) {
        ensureInfosIsMutable();
        infos_.add(index, builderForValue.build());
        onChanged();
      } else {
        infosBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .proto.UpdateInfo infos = 1;</code>
     */
    public Builder addAllInfos(
        Iterable<? extends UpdateInfo> values) {
      if (infosBuilder_ == null) {
        ensureInfosIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, infos_);
        onChanged();
      } else {
        infosBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .proto.UpdateInfo infos = 1;</code>
     */
    public Builder clearInfos() {
      if (infosBuilder_ == null) {
        infos_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        infosBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .proto.UpdateInfo infos = 1;</code>
     */
    public Builder removeInfos(int index) {
      if (infosBuilder_ == null) {
        ensureInfosIsMutable();
        infos_.remove(index);
        onChanged();
      } else {
        infosBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .proto.UpdateInfo infos = 1;</code>
     */
    public UpdateInfo.Builder getInfosBuilder(
        int index) {
      return getInfosFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .proto.UpdateInfo infos = 1;</code>
     */
    public UpdateInfoOrBuilder getInfosOrBuilder(
        int index) {
      if (infosBuilder_ == null) {
        return infos_.get(index);  } else {
        return infosBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .proto.UpdateInfo infos = 1;</code>
     */
    public java.util.List<? extends UpdateInfoOrBuilder>
         getInfosOrBuilderList() {
      if (infosBuilder_ != null) {
        return infosBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(infos_);
      }
    }
    /**
     * <code>repeated .proto.UpdateInfo infos = 1;</code>
     */
    public UpdateInfo.Builder addInfosBuilder() {
      return getInfosFieldBuilder().addBuilder(
          UpdateInfo.getDefaultInstance());
    }
    /**
     * <code>repeated .proto.UpdateInfo infos = 1;</code>
     */
    public UpdateInfo.Builder addInfosBuilder(
        int index) {
      return getInfosFieldBuilder().addBuilder(
          index, UpdateInfo.getDefaultInstance());
    }
    /**
     * <code>repeated .proto.UpdateInfo infos = 1;</code>
     */
    public java.util.List<UpdateInfo.Builder>
         getInfosBuilderList() {
      return getInfosFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        UpdateInfo, UpdateInfo.Builder, UpdateInfoOrBuilder>
        getInfosFieldBuilder() {
      if (infosBuilder_ == null) {
        infosBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            UpdateInfo, UpdateInfo.Builder, UpdateInfoOrBuilder>(
                infos_,
                ((bitField0_ & 0x00000001) == 0x00000001),
                getParentForChildren(),
                isClean());
        infos_ = null;
      }
      return infosBuilder_;
    }
    @Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFieldsProto3(unknownFields);
    }

    @Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:proto.UpdateInfoList)
  }

  // @@protoc_insertion_point(class_scope:proto.UpdateInfoList)
  private static final UpdateInfoList DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new UpdateInfoList();
  }

  public static UpdateInfoList getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<UpdateInfoList>
      PARSER = new com.google.protobuf.AbstractParser<UpdateInfoList>() {
    @Override
    public UpdateInfoList parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new UpdateInfoList(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<UpdateInfoList> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<UpdateInfoList> getParserForType() {
    return PARSER;
  }

  @Override
  public UpdateInfoList getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
