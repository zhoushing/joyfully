<template>
  <div style="width: 100%; padding: 10px">
    <!--功能区域-->
    <div style="margin: 10px 0;">
      <el-button type="primary" @click="addQuestion">新增</el-button>
    </div>
    <!--搜索区域-->
    <div style="margin: 10px 0;">
      <el-input v-model="search" placeholder="请输入要搜索的问题分类" style="width: 20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>
    <!--加上border就会表单溢出-->
    <el-table
        :data="tableData"

        style="width: 100%"
        stripe>
      <el-table-column type="expand">
        <template #default="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="问题">
              <span>{{ props.row.issue }}</span>
            </el-form-item>

            <br />
            <el-form-item
                label="答案：">
              <el-button size="mini" type="success" plain @click="showAnswer(props.row.answerList)">
                查看
              </el-button>
            </el-form-item>

          </el-form>
        </template>
      </el-table-column>

      <el-table-column
          prop="id"
          label="ID"
          sortable>
      </el-table-column>
      <el-table-column
          prop="type"
          label="题型"
          :filters="[
              { text: '选择题', value: '选择题' },
              { text: '填空题', value: '填空题' },
              { text: '问答题', value: '问答题' },
              { text: '主观题', value: '主观题' },
              { text: '其他', value: '其他' }]"
          :filter-method="filterTag"
          filter-placement="bottom-end">
        <template #default="scope">
          <el-tag
              :type="scope.row.type === '问答题' ? 'primary' : 'success'"
              disable-transitions>{{scope.row.type}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
          prop="category"
          label="问题分类"
          sortable>
      </el-table-column>
      <el-table-column
          prop="share"
          label="是否分享">
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

      <el-dialog title="答案列表" v-model="answerVis" width="80%">
        <el-table :data="answerList" stripe border>
          <el-table-column prop="id" label="答案ID"></el-table-column>
          <el-table-column prop="userId" label="所属用户ID"></el-table-column>
          <el-table-column prop="userName" label="所属用户昵称"></el-table-column>
          <el-table-column prop="content" label="答案内容" width="400"></el-table-column>
        </el-table>
      </el-dialog>

      <el-dialog title="提示" v-model="dialogVisible" width="30%">
        <el-form ref="form" :model="form" label-width="120px" :rules="rules">
          <el-form-item label="问题分类" prop="category">
            <el-input v-model="form.category" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="题型" prop="type">
            <el-select v-model="form.type" style="width: 80%" placeholder="请选择题目类型">
              <el-option label="选择题" value="选择题"></el-option>
              <el-option label="填空题" value="填空题"></el-option>
              <el-option label="问答题" value="问答题"></el-option>
              <el-option label="主观题" value="主观题"></el-option>
              <el-option label="其他" value="其他"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="问题" prop="issue">
            <el-input type="textarea" autosize v-model="form.issue" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="答案">
            <el-input type="textarea" autosize v-model="form.answer" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="是否分享" prop="share">
            <el-radio v-model="form.share" label="true">是</el-radio>
            <el-radio v-model="form.share" label="false">否</el-radio>
          </el-form-item>
        </el-form>
        <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveQuestion">确 定</el-button>
        </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>

import request from "../../utils/request";

export default {
  name: 'UserQuestion',
  components: {},
  data() {
    return {
      form: {
        id: '',
        issue: '',
        answer: '',
        category: '',
        type: '',
        share: '',
        userId: ''
      },
      dialogVisible: false,
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 10,
      tableData: [],
      rules: {
        issue: [
          {required: true, message: '请填写问题', trigger: 'blur'}
        ],
        type: [
          {required: true, message: '请选择题目类型', trigger: 'blur'}
        ],
        share: [
          {required: true, message: '请选择是否分享', trigger: 'blur'}
        ],
      },
      answerVis: false,
      answerList: [],
    }
  },
  /* 初始加载方法 */
  created() {
    let str = sessionStorage.getItem("user") || "{}";
    this.form.userId = JSON.parse(str).id;
    this.load();
  },
  methods: {
    load() {
      request.get("/question/findByUserId", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
          userId: this.form.userId,
        }
      }).then(res => {
        this.tableData = res.data.records;
        this.total = res.data.total;
        console.log(res.data);
      });
    },
    addQuestion: function () {
      this.dialogVisible = true;
      this.form.id = '';
      this.form.issue = '';
      this.form.answer = '';
      this.form.category = '';
      this.form.type = '';
    },
    saveQuestion() {
      console.log(this.form.id);
      /* 更新 */
      if (this.form.id) {
        request.put("/question/update", this.form).then(res => {
          if (res.code === '0') {
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
        request.post("/question/save", this.form).then(res => {
          if (res.code === '0') {
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
    handleDelete: function (id) {
      request.delete("/question/delete/" + id).then(res => {
        if (res.code === '0') {
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
    showAnswer(answerList) {
      this.answerList = answerList
      this.answerVis = true
    },
  }
}
</script>
