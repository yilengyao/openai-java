package com.yilengyao.openai.model.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Model {

  public String id;
  public String object;
  @JsonProperty("owned_by")
  public String ownedBy;
  public long created;
  public List<Permission> permission;
  public String root;
  public String parent;

  public com.yilengyao.openai.graphql.generated.types.Model toGraphQl() {
    return com.yilengyao.openai.graphql.generated.types.Model.newBuilder()
        .id(this.getId())
        .object(this.getObject())
        .created(this.getCreated())
        .ownedBy(this.getOwnedBy())
        .permission(this.getPermission().stream().map(Permission::toGraphQl).toList())
        ._root(this.getRoot())
        ._parent(this.getParent())
        .build();
  }

}
