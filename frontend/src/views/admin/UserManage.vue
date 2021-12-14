<template>
  <div style="width: 100%; padding: 10px">
    <el-backtop />
    <!--功能区域-->
    <div style="margin: 10px 0;">
      <el-button type="primary" @click="addUser">新增</el-button>
      <el-upload
          action="http://localhost:9090/user/import"
          :on-success="uploadSuccess"
          :show-file-list=false
          :limit="1"
          accept='.xlsx'
          style="display: inline-block; margin: 0 10px"
      >
        <el-button type="primary">导入</el-button>
      </el-upload>
      <el-button type="primary" @click="exportUser">导出</el-button>
    </div>
    <!--搜索区域-->
    <div style="margin: 10px 0;">
      <el-input v-model="search" placeholder="请输入要搜索的昵称" style="width: 20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>
    <!--加上border就会表单溢出-->
    <el-table
        :data="tableData"
        style="width: 98%"
        stripe
        v-loading="isLoad">
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
          prop="nickName"
          label="昵称">
      </el-table-column>
      <el-table-column
          prop="birthday"
          label="出生日期">
      </el-table-column>
      <el-table-column
          prop="sex"
          label="性别">
      </el-table-column>
      <el-table-column
          prop="address"
          label="地址">
      </el-table-column>
      <el-table-column
          prop="question"
          label="问题列表">
        <template #default="scope">
        <el-button size="mini" type="success" plain @click="showQuestions(scope.row.questionList)">
          查看
        </el-button>
        </template>
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

      <el-dialog title="用户的问题列表" v-model="questionVis" width="80%">
        <el-table :data="questionList" stripe border>
          <el-table-column prop="id" label="ID"></el-table-column>
          <el-table-column prop="issue" label="问题" width="400"></el-table-column>
          <el-table-column prop="category" label="类别"></el-table-column>
          <el-table-column prop="type" label="类型"></el-table-column>
          <el-table-column prop="share" label="是否分享"></el-table-column>
        </el-table>
      </el-dialog>

      <el-dialog title="提示" v-model="dialogVisible" width="30%">
        <el-form :model = "form" label-width="120px" :rules="rules">
          <el-form-item label="用户名" prop="name">
            <el-input v-model="form.name" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="昵称">
            <el-input v-model="form.nickName" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="出生日期">
            <el-date-picker
                v-model="form.birthday"
                type="date"
                placeholder="选择日期"
                value-format="YYYY-MM-DD">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio v-model="form.sex" label="男">男</el-radio>
            <el-radio v-model="form.sex" label="女">女</el-radio>
            <el-radio v-model="form.sex" label="未知">未知</el-radio>
          </el-form-item>
          <el-form-item label="地址">
            <el-input type="textarea" v-model="form.address" style="width: 80%"></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveUser">确 定</el-button>
        </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>

import request from "../../utils/request";

export default {
  name: 'User',
  components: {

  },
  data() {
    return {
      form: {
        name: '',
        nickName: '',
        birthday: '',
        sex: ''
      },
      dialogVisible: false,
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      isLoad: false,
      rules: {
        name: [
          {required: true, message: '请填写用户名', trigger: 'blur'}
        ]
      },
      questionList: [],
      questionVis: false
    }
  },
  /* 初始加载方法 */
  created() {
    this.isLoad = true;
    this.load();
    this.isLoad = false;
  },
  methods: {
    load() {
      request.get("/user/find", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        console.log(res);
        this.tableData = res.data.records;
        this.total = res.data.total;
      });
    },
    addUser: function () {
      this.dialogVisible = true;
      this.form = {}
    },
    saveUser () {
      /* 更新 */
      if (this.form.id) {
        request.put("/user/update", this.form).then(res => {
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
        request.post("/user/save", this.form).then(res => {
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
      request.delete("/user/delete/" + id).then(res => {
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
    },
    uploadSuccess(res) {
      if (res.code === "0") {
        this.$message.success("导入成功");
        this.load();
      }
      else {
        this.$message.error("导入失败");
      }
    },
    /* todo window.server.fileUploadUrl window.server.port */
    exportUser() {
      location.href = "http://" + "localhost" + ":" + "9090" + "/user/export"
    },
    showQuestions(questions) {
      this.questionList = questions;
      this.questionVis = true;
    }
  }
}
</script>

























































