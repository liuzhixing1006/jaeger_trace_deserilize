// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: httpbody.proto

package com.huya.servicechain.domain.grpc;

/**
 * @ClassName HttpBodyProto
 * @Description Trace指标转换逻辑
 * @Author liuzhixing
 * @Date 2019-09-18 17:59
 **/
public final class HttpBodyProto {
  private HttpBodyProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_api_HttpBody_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_api_HttpBody_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\016httpbody.proto\022\ngoogle.api\032\031google/pro" +
      "tobuf/any.proto\"X\n\010HttpBody\022\024\n\014content_t" +
      "ype\030\001 \001(\t\022\014\n\004data\030\002 \001(\014\022(\n\nextensions\030\003 " +
      "\003(\0132\024.google.protobuf.AnyB{\n!com.huya.se" +
      "rvicechain.domain.grpcB\rHttpBodyProtoP\001Z" +
      ";google.golang.org/genproto/googleapis/a" +
      "pi/httpbody;httpbody\370\001\001\242\002\004GAPIb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.protobuf.AnyProto.getDescriptor(),
        });
    internal_static_google_api_HttpBody_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_google_api_HttpBody_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_api_HttpBody_descriptor,
        new java.lang.String[] { "ContentType", "Data", "Extensions", });
    com.google.protobuf.AnyProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
