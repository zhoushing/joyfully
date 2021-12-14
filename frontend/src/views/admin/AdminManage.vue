<template>
  <div style="width: 100%; padding: 10px">
    <el-backtop />
    <!--功能区域-->
    <div style="margin: 10px 0;">
      <el-button type="primary" @click="addAdmin">新增</el-button>
    </div>
    <!--搜索区域-->
    <div style="margin: 10px 0;">
      <el-input v-model="search" placeholder="请输入要搜索的内容" style="width: 20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>
    <!--加上border就会表单溢出-->
    <el-table
        :data="tableData"
        style="width: 100%"
        stripe>
      <el-table-column
          prop="id"
          label="ID"
          sortable>
      </el-table-column>
      <el-table-column
          prop="name"
          label="用户名">
      </el-table-column>
      <el-table-column
          prop="power"
          label="权限">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作">
        <template #default="scope">
          <el-button @click="handleEdit(scope.row)" type="primary" size="mini">编辑</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button type="danger" size="mini">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>

      <el-dialog title="提示" v-model="dialogVisible" width="30%">
        <el-form :model = "form" label-width="120px">
          <el-form-item label="用户名">
            <el-input v-model="form.name" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="权限">
            <el-radio v-model="form.power" label=true>true</el-radio>
            <el-radio v-model="form.power" label=false>false</el-radio>
          </el-form-item>
        </el-form>
        <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveAdmin">确 定</el-button>
        </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>

import request from "../../utils/request";
import router from "../../router";

export default {
  name: 'Admin',
  components: {

  },
  data() {
    return {
      form: {
        name: '',
        power: '',
      },
      dialogVisible: false,
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
    }
  },
  /* 初始加载方法 */
  created() {
    this.load();
  },
  methods: {
    load() {
      request.get("/admin/find", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        this.tableData = res.data.records;
        this.total = res.data.total
      });
    },
    addAdmin: function () {
      this.dialogVisible = true;
      this.form = {}
    },
    saveAdmin () {
      /* 更新 */
      if (this.form.id) {
        request.put("/admin/update", this.form).then(res => {
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "更新成功"
            });
          }
          else {
            this.$message({
              type: "error",
              message: res.msg
            });
          }
        });
      }
      else {
        /* 新增 */
        request.post("/admin/save", this.form).then(res => {
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "添加成功"
            });
          }
          else {
            this.$message({
              type: "error",
              message: res.msg
            });
          }
        });
      }
      this.dialogVisible = false;
      this.search = '';
      this.load();
    },
    handleEdit (row) {
      /* 深拷贝 */
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogVisible = true;
    },
    handleDelete: function (id) {
      request.delete("/admin/delete/" + id).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "删除成功"
          });
        }
        else {
          this.$message({
            type: "error",
            message: res.msg
          });
        }
        this.load();
      });
    },
    /* 改变每页的总个数时触发 */
    handleSizeChange: function (pageSize) {
      this.pageSize = pageSize;
      this.load();
    },
    /* 改变当前页码触发 */
    handleCurrentChange: function (pageNum) {
      this.currentPage = pageNum;
      this.load();
    }
  }
}
</script>

























































