// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: http.proto

package com.huya.servicechain.domain.grpc;

/**
 * <pre>
 * Defines the HTTP configuration for an API service. It contains a list of
 * [HttpRule][google.api.HttpRule], each specifying the mapping of an RPC method
 * to one or more HTTP REST API methods.
 * </pre>
 *
 * Protobuf type {@code google.api.Http}
 */

/**
 * @ClassName Http
 * @Description Trace指标转换逻辑
 * @Author liuzhixing
 * @Date 2019-09-18 17:59
 **/
public  final class Http extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:google.api.Http)
    HttpOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Http.newBuilder() to construct.
  private Http(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Http() {
    rules_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new Http();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private Http(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
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
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              rules_ = new java.util.ArrayList<com.huya.servicechain.domain.grpc.HttpRule>();
              mutable_bitField0_ |= 0x00000001;
            }
            rules_.add(
                input.readMessage(com.huya.servicechain.domain.grpc.HttpRule.parser(), extensionRegistry));
            break;
          }
          case 16: {

            fullyDecodeReservedExpansion_ = input.readBool();
            break;
          }
          default: {
            if (!parseUnknownField(
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
      if (((mutable_bitField0_ & 0x00000001) != 0)) {
        rules_ = java.util.Collections.unmodifiableList(rules_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.huya.servicechain.domain.grpc.HttpProto.internal_static_google_api_Http_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.huya.servicechain.domain.grpc.HttpProto.internal_static_google_api_Http_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.huya.servicechain.domain.grpc.Http.class, com.huya.servicechain.domain.grpc.Http.Builder.class);
  }

  public static final int RULES_FIELD_NUMBER = 1;
  private java.util.List<com.huya.servicechain.domain.grpc.HttpRule> rules_;
  /**
   * <pre>
   * A list of HTTP configuration rules that apply to individual API methods.
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.HttpRule rules = 1;</code>
   */
  public java.util.List<com.huya.servicechain.domain.grpc.HttpRule> getRulesList() {
    return rules_;
  }
  /**
   * <pre>
   * A list of HTTP configuration rules that apply to individual API methods.
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.HttpRule rules = 1;</code>
   */
  public java.util.List<? extends com.huya.servicechain.domain.grpc.HttpRuleOrBuilder> 
      getRulesOrBuilderList() {
    return rules_;
  }
  /**
   * <pre>
   * A list of HTTP configuration rules that apply to individual API methods.
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.HttpRule rules = 1;</code>
   */
  public int getRulesCount() {
    return rules_.size();
  }
  /**
   * <pre>
   * A list of HTTP configuration rules that apply to individual API methods.
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.HttpRule rules = 1;</code>
   */
  public com.huya.servicechain.domain.grpc.HttpRule getRules(int index) {
    return rules_.get(index);
  }
  /**
   * <pre>
   * A list of HTTP configuration rules that apply to individual API methods.
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.HttpRule rules = 1;</code>
   */
  public com.huya.servicechain.domain.grpc.HttpRuleOrBuilder getRulesOrBuilder(
      int index) {
    return rules_.get(index);
  }

  public static final int FULLY_DECODE_RESERVED_EXPANSION_FIELD_NUMBER = 2;
  private boolean fullyDecodeReservedExpansion_;
  /**
   * <pre>
   * When set to true, URL path parmeters will be fully URI-decoded except in
   * cases of single segment matches in reserved expansion, where "%2F" will be
   * left encoded.
   * The default behavior is to not decode RFC 6570 reserved characters in multi
   * segment matches.
   * </pre>
   *
   * <code>bool fully_decode_reserved_expansion = 2;</code>
   */
  public boolean getFullyDecodeReservedExpansion() {
    return fullyDecodeReservedExpansion_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    for (int i = 0; i < rules_.size(); i++) {
      output.writeMessage(1, rules_.get(i));
    }
    if (fullyDecodeReservedExpansion_ != false) {
      output.writeBool(2, fullyDecodeReservedExpansion_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < rules_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, rules_.get(i));
    }
    if (fullyDecodeReservedExpansion_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(2, fullyDecodeReservedExpansion_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.huya.servicechain.domain.grpc.Http)) {
      return super.equals(obj);
    }
    com.huya.servicechain.domain.grpc.Http other = (com.huya.servicechain.domain.grpc.Http) obj;

    if (!getRulesList()
        .equals(other.getRulesList())) return false;
    if (getFullyDecodeReservedExpansion()
        != other.getFullyDecodeReservedExpansion()) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (getRulesCount() > 0) {
      hash = (37 * hash) + RULES_FIELD_NUMBER;
      hash = (53 * hash) + getRulesList().hashCode();
    }
    hash = (37 * hash) + FULLY_DECODE_RESERVED_EXPANSION_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getFullyDecodeReservedExpansion());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.huya.servicechain.domain.grpc.Http parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.huya.servicechain.domain.grpc.Http parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.huya.servicechain.domain.grpc.Http parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.huya.servicechain.domain.grpc.Http parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.huya.servicechain.domain.grpc.Http parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.huya.servicechain.domain.grpc.Http parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.huya.servicechain.domain.grpc.Http parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.huya.servicechain.domain.grpc.Http parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.huya.servicechain.domain.grpc.Http parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.huya.servicechain.domain.grpc.Http parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.huya.servicechain.domain.grpc.Http parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.huya.servicechain.domain.grpc.Http parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.huya.servicechain.domain.grpc.Http prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * Defines the HTTP configuration for an API service. It contains a list of
   * [HttpRule][google.api.HttpRule], each specifying the mapping of an RPC method
   * to one or more HTTP REST API methods.
   * </pre>
   *
   * Protobuf type {@code google.api.Http}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:google.api.Http)
      com.huya.servicechain.domain.grpc.HttpOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.huya.servicechain.domain.grpc.HttpProto.internal_static_google_api_Http_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.huya.servicechain.domain.grpc.HttpProto.internal_static_google_api_Http_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.huya.servicechain.domain.grpc.Http.class, com.huya.servicechain.domain.grpc.Http.Builder.class);
    }

    // Construct using com.huya.servicechain.domain.grpc.Http.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
        getRulesFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (rulesBuilder_ == null) {
        rules_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        rulesBuilder_.clear();
      }
      fullyDecodeReservedExpansion_ = false;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.huya.servicechain.domain.grpc.HttpProto.internal_static_google_api_Http_descriptor;
    }

    @java.lang.Override
    public com.huya.servicechain.domain.grpc.Http getDefaultInstanceForType() {
      return com.huya.servicechain.domain.grpc.Http.getDefaultInstance();
    }

    @java.lang.Override
    public com.huya.servicechain.domain.grpc.Http build() {
      com.huya.servicechain.domain.grpc.Http result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.huya.servicechain.domain.grpc.Http buildPartial() {
      com.huya.servicechain.domain.grpc.Http result = new com.huya.servicechain.domain.grpc.Http(this);
      int from_bitField0_ = bitField0_;
      if (rulesBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          rules_ = java.util.Collections.unmodifiableList(rules_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.rules_ = rules_;
      } else {
        result.rules_ = rulesBuilder_.build();
      }
      result.fullyDecodeReservedExpansion_ = fullyDecodeReservedExpansion_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.huya.servicechain.domain.grpc.Http) {
        return mergeFrom((com.huya.servicechain.domain.grpc.Http)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.huya.servicechain.domain.grpc.Http other) {
      if (other == com.huya.servicechain.domain.grpc.Http.getDefaultInstance()) return this;
      if (rulesBuilder_ == null) {
        if (!other.rules_.isEmpty()) {
          if (rules_.isEmpty()) {
            rules_ = other.rules_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureRulesIsMutable();
            rules_.addAll(other.rules_);
          }
          onChanged();
        }
      } else {
        if (!other.rules_.isEmpty()) {
          if (rulesBuilder_.isEmpty()) {
            rulesBuilder_.dispose();
            rulesBuilder_ = null;
            rules_ = other.rules_;
            bitField0_ = (bitField0_ & ~0x00000001);
            rulesBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getRulesFieldBuilder() : null;
          } else {
            rulesBuilder_.addAllMessages(other.rules_);
          }
        }
      }
      if (other.getFullyDecodeReservedExpansion() != false) {
        setFullyDecodeReservedExpansion(other.getFullyDecodeReservedExpansion());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.huya.servicechain.domain.grpc.Http parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.huya.servicechain.domain.grpc.Http) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<com.huya.servicechain.domain.grpc.HttpRule> rules_ =
      java.util.Collections.emptyList();
    private void ensureRulesIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        rules_ = new java.util.ArrayList<com.huya.servicechain.domain.grpc.HttpRule>(rules_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.huya.servicechain.domain.grpc.HttpRule, com.huya.servicechain.domain.grpc.HttpRule.Builder, com.huya.servicechain.domain.grpc.HttpRuleOrBuilder> rulesBuilder_;

    /**
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public java.util.List<com.huya.servicechain.domain.grpc.HttpRule> getRulesList() {
      if (rulesBuilder_ == null) {
        return java.util.Collections.unmodifiableList(rules_);
      } else {
        return rulesBuilder_.getMessageList();
      }
    }
    /**
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public int getRulesCount() {
      if (rulesBuilder_ == null) {
        return rules_.size();
      } else {
        return rulesBuilder_.getCount();
      }
    }
    /**
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public com.huya.servicechain.domain.grpc.HttpRule getRules(int index) {
      if (rulesBuilder_ == null) {
        return rules_.get(index);
      } else {
        return rulesBuilder_.getMessage(index);
      }
    }
    /**
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public Builder setRules(
        int index, com.huya.servicechain.domain.grpc.HttpRule value) {
      if (rulesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureRulesIsMutable();
        rules_.set(index, value);
        onChanged();
      } else {
        rulesBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public Builder setRules(
        int index, com.huya.servicechain.domain.grpc.HttpRule.Builder builderForValue) {
      if (rulesBuilder_ == null) {
        ensureRulesIsMutable();
        rules_.set(index, builderForValue.build());
        onChanged();
      } else {
        rulesBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public Builder addRules(com.huya.servicechain.domain.grpc.HttpRule value) {
      if (rulesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureRulesIsMutable();
        rules_.add(value);
        onChanged();
      } else {
        rulesBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public Builder addRules(
        int index, com.huya.servicechain.domain.grpc.HttpRule value) {
      if (rulesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureRulesIsMutable();
        rules_.add(index, value);
        onChanged();
      } else {
        rulesBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public Builder addRules(
        com.huya.servicechain.domain.grpc.HttpRule.Builder builderForValue) {
      if (rulesBuilder_ == null) {
        ensureRulesIsMutable();
        rules_.add(builderForValue.build());
        onChanged();
      } else {
        rulesBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public Builder addRules(
        int index, com.huya.servicechain.domain.grpc.HttpRule.Builder builderForValue) {
      if (rulesBuilder_ == null) {
        ensureRulesIsMutable();
        rules_.add(index, builderForValue.build());
        onChanged();
      } else {
        rulesBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public Builder addAllRules(
        java.lang.Iterable<? extends com.huya.servicechain.domain.grpc.HttpRule> values) {
      if (rulesBuilder_ == null) {
        ensureRulesIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, rules_);
        onChanged();
      } else {
        rulesBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public Builder clearRules() {
      if (rulesBuilder_ == null) {
        rules_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        rulesBuilder_.clear();
      }
      return this;
    }
    /**
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public Builder removeRules(int index) {
      if (rulesBuilder_ == null) {
        ensureRulesIsMutable();
        rules_.remove(index);
        onChanged();
      } else {
        rulesBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public com.huya.servicechain.domain.grpc.HttpRule.Builder getRulesBuilder(
        int index) {
      return getRulesFieldBuilder().getBuilder(index);
    }
    /**
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public com.huya.servicechain.domain.grpc.HttpRuleOrBuilder getRulesOrBuilder(
        int index) {
      if (rulesBuilder_ == null) {
        return rules_.get(index);  } else {
        return rulesBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public java.util.List<? extends com.huya.servicechain.domain.grpc.HttpRuleOrBuilder> 
         getRulesOrBuilderList() {
      if (rulesBuilder_ != null) {
        return rulesBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(rules_);
      }
    }
    /**
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public com.huya.servicechain.domain.grpc.HttpRule.Builder addRulesBuilder() {
      return getRulesFieldBuilder().addBuilder(
          com.huya.servicechain.domain.grpc.HttpRule.getDefaultInstance());
    }
    /**
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public com.huya.servicechain.domain.grpc.HttpRule.Builder addRulesBuilder(
        int index) {
      return getRulesFieldBuilder().addBuilder(
          index, com.huya.servicechain.domain.grpc.HttpRule.getDefaultInstance());
    }
    /**
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public java.util.List<com.huya.servicechain.domain.grpc.HttpRule.Builder> 
         getRulesBuilderList() {
      return getRulesFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.huya.servicechain.domain.grpc.HttpRule, com.huya.servicechain.domain.grpc.HttpRule.Builder, com.huya.servicechain.domain.grpc.HttpRuleOrBuilder> 
        getRulesFieldBuilder() {
      if (rulesBuilder_ == null) {
        rulesBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            com.huya.servicechain.domain.grpc.HttpRule, com.huya.servicechain.domain.grpc.HttpRule.Builder, com.huya.servicechain.domain.grpc.HttpRuleOrBuilder>(
                rules_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        rules_ = null;
      }
      return rulesBuilder_;
    }

    private boolean fullyDecodeReservedExpansion_ ;
    /**
     * <pre>
     * When set to true, URL path parmeters will be fully URI-decoded except in
     * cases of single segment matches in reserved expansion, where "%2F" will be
     * left encoded.
     * The default behavior is to not decode RFC 6570 reserved characters in multi
     * segment matches.
     * </pre>
     *
     * <code>bool fully_decode_reserved_expansion = 2;</code>
     */
    public boolean getFullyDecodeReservedExpansion() {
      return fullyDecodeReservedExpansion_;
    }
    /**
     * <pre>
     * When set to true, URL path parmeters will be fully URI-decoded except in
     * cases of single segment matches in reserved expansion, where "%2F" will be
     * left encoded.
     * The default behavior is to not decode RFC 6570 reserved characters in multi
     * segment matches.
     * </pre>
     *
     * <code>bool fully_decode_reserved_expansion = 2;</code>
     */
    public Builder setFullyDecodeReservedExpansion(boolean value) {
      
      fullyDecodeReservedExpansion_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * When set to true, URL path parmeters will be fully URI-decoded except in
     * cases of single segment matches in reserved expansion, where "%2F" will be
     * left encoded.
     * The default behavior is to not decode RFC 6570 reserved characters in multi
     * segment matches.
     * </pre>
     *
     * <code>bool fully_decode_reserved_expansion = 2;</code>
     */
    public Builder clearFullyDecodeReservedExpansion() {
      
      fullyDecodeReservedExpansion_ = false;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:google.api.Http)
  }

  // @@protoc_insertion_point(class_scope:google.api.Http)
  private static final com.huya.servicechain.domain.grpc.Http DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.huya.servicechain.domain.grpc.Http();
  }

  public static com.huya.servicechain.domain.grpc.Http getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Http>
      PARSER = new com.google.protobuf.AbstractParser<Http>() {
    @java.lang.Override
    public Http parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new Http(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Http> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Http> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.huya.servicechain.domain.grpc.Http getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

