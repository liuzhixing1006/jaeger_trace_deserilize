// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: http.proto

package com.github.liuzhixing.domain.grpc;

/**
 * @ClassName CustomHttpPatternOrBuilder
 * @Description Trace指标转换逻辑
 * @Author liuzhixing
 * @Date 2019-09-18 17:59
 **/
public interface CustomHttpPatternOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.api.CustomHttpPattern)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The name of this custom HTTP verb.
   * </pre>
   *
   * <code>string kind = 1;</code>
   */
  java.lang.String getKind();
  /**
   * <pre>
   * The name of this custom HTTP verb.
   * </pre>
   *
   * <code>string kind = 1;</code>
   */
  com.google.protobuf.ByteString
      getKindBytes();

  /**
   * <pre>
   * The path matched by this custom verb.
   * </pre>
   *
   * <code>string path = 2;</code>
   */
  java.lang.String getPath();
  /**
   * <pre>
   * The path matched by this custom verb.
   * </pre>
   *
   * <code>string path = 2;</code>
   */
  com.google.protobuf.ByteString
      getPathBytes();
}
