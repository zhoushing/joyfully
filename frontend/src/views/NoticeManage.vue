<template>
  <div style="width: 100%; padding: 10px">
    <!--功能区域-->
    <div style="margin: 10px 0;">
      <el-button type="primary" @click="addNotice">发布公告</el-button>
    </div>
    <!--搜索区域-->
    <div style="margin: 10px 0;">
      <el-input v-model="search" placeholder="请输入要搜索的公告标题" style="width: 20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>

    <el-scrollbar max-height="68vh" style="height: 68vh">

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
            prop="title"
            label="标题">
        </el-table-column>
        <el-table-column
            type="expand"
            width="120px"
            label="公告内容">
          <template #default="props">
              <span>{{ props.row.content }}</span>
          </template>
        </el-table-column>
        <el-table-column
            prop="date"
            label="发布时间"
            sortable>
        </el-table-column>
        <el-table-column
            prop="publisher"
            label="发布人">
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
            <el-button @click="handleTop(scope.row)" type="" size="mini">
              {{ scope.row.priority === 1? '置顶' : '取消置顶'}}
            </el-button>
          </template>
        </el-table-column>
      </el-table>

    </el-scrollbar>

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

      <el-dialog center title="发布公告" v-model="dialogVisible" width="30%">
        <el-form ref="form" :model="form" label-width="120px" :rules="rules">
          <el-form-item label="公告标题" prop="title">
            <el-input v-model="form.title" style="width: 90%" clearable></el-input>
          </el-form-item>
          <el-form-item label="公告内容" prop="content">
            <el-input type="textarea" autosize v-model="form.content" style="width: 90%" clearable></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveNotice">确 定</el-button>
        </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>

import request from "../utils/request";

export default {
  name: 'NoticeManage',
  components: {},
  data() {
    return {
      form: {
      },
      dialogVisible: false,
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 10,
      tableData: [],
      isLoad: false,
      rules: {
        title: [
          {required: true, message: '请填写公告标题', trigger: 'blur'}
        ],
        content: [
          {required: true, message: '请填写公告内容', trigger: 'blur'}
        ],
      },
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
      request.get("/notice", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        if (res.code === '00000') {
          this.tableData = res.data.records;
          this.total = res.data.total;
        }
        else {
          this.$message.error(res.msg);
        }
      });
    },
    addNotice: function () {
      this.dialogVisible = true;
      this.form = {}
    },
    saveNotice() {
      /* 更新 */
      if (this.form.id) {
        request.put("/notice/update", this.form).then(res => {
          if (res.code === '00000') {
            this.$message({
              type: "success",
              message: "更新成功"
            });
          } else {
            this.$message({
              type: "error",
              message: res.msg
            });
          }
        });
      } else {
        /* 新增 */
        request.post("/notice/save", this.form).then(res => {
          if (res.code === '00000') {
            this.$message({
              type: "success",
              message: "添加成功"
            });
          } else {
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
    handleEdit(row) {
      /* 深拷贝 */
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogVisible = true;
    },
    handleTop(row) {
      request.put("/notice/top", row).then(res => {
        if (res.code === '00000') {
          console.log(res.data === 1)
          this.$message({
            type: "success",
            message: (res.data === 1? '取消': '') + "置顶成功"
          });
        } else {
          this.$message({
            type: "error",
            message: res.msg
          });
        }
        this.load();
      });
    },
    handleDelete: function (id) {
      request.delete("/notice/delete/" + id).then(res => {
        if (res.code === '00000') {
          this.$message({
            type: "success",
            message: "删除成功"
          });
        } else {
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
    filterTag(value, row) {
      return row.type === value;
    },
  }
}
</script>
