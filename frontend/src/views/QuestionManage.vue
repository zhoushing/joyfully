<template>
  <div style="width: 100%; padding: 10px">
    <!--功能区域-->
    <div style="margin: 10px 0;">
      <el-button type="primary" @click="addQuestion">新增问题</el-button>
      <el-button type="primary" @click="addCategory">新增问题类别</el-button>
    </div>
    <!--搜索区域-->
    <div style="margin: 10px 0;">
      <el-input v-model="search" placeholder="请输入要搜索的用户id" style="width: 20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>

    <el-scrollbar max-height="68vh" style="height: 68vh">

      <!--加上border就会表单溢出-->
      <el-table
          :data="tableData"
          style="width: 98%"
          stripe
          v-loading="isLoad">

        <el-table-column type="expand">
          <template #default="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="问题：">
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
            prop="categoryList"
            width="200px"
            label="问题分类">
          <template #default="scope">
            <el-tag v-for="c in scope.row.categoryList" :key="c.id" :type="'primary'">
              {{c.name}}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column
            prop="userId"
            label="所属用户id"
            width="120px"
            sortable>
        </el-table-column>
        <el-table-column
            prop="share"
            label="是否分享">
        </el-table-column>
        <el-table-column
            prop="praiseCount"
            label="点赞数">
        </el-table-column>
        <el-table-column
            prop="belittleCount"
            label="点踩数">
        </el-table-column>
        <el-table-column
            prop="attentionCount"
            label="关注数">
        </el-table-column>
        <el-table-column
            fixed="right"
            width="160px"
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

      <el-dialog center title="答案列表" v-model="answerVis" width="80%">
        <el-table :data="answerList" stripe border>
          <el-table-column prop="userId" label="所属用户ID" width="100px"></el-table-column>
          <el-table-column prop="userNickname" label="所属用户昵称"></el-table-column>
          <el-table-column prop="content" label="答案内容"></el-table-column>
          <el-table-column prop="praiseCount" label="点赞数"></el-table-column>
          <el-table-column prop="belittleCount" label="点踩数"></el-table-column>
          <el-table-column label="操作">
            <template #default="scope">
              <el-popconfirm title="确定删除吗？" @confirm="answerDelete(scope.$index, scope.row)">
                <template #reference>
                  <el-button type="danger" size="mini">删除</el-button>
                </template>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
      </el-dialog>

      <el-dialog center title="问题" v-model="dialogVisible" width="30%">
        <el-form ref="form" :model="form" label-width="120px" :rules="rules">
          <el-form-item label="问题分类" prop="category">
            <el-tag v-for="c in form.categoryList" :key="c.id" :type="'primary'">
              {{c.name}}
            </el-tag>
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
<!--          <el-form-item label="所属用户id" prop="userId">
            <el-input v-model="form.userId" style="width: 80%"></el-input>
          </el-form-item>-->
          <el-form-item label="问题" prop="issue">
            <el-input type="textarea" autosize v-model="form.issue" style="width: 90%"></el-input>
          </el-form-item>
<!--          <el-form-item label="答案">
            <el-input type="textarea" autosize v-model="form.answer" style="width: 90%"></el-input>
          </el-form-item>-->
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

      <el-dialog center title="问题类别" v-model="categoryVis" width="30%">
        <el-row :gutter="20">
          <el-col :span="14">
            <el-table :data="categoryList" style="width: 100%" height="40vh">
              <el-table-column prop="name" label="已有类别">
              </el-table-column>
            </el-table>
          </el-col>
          <el-col :span="10" style="padding-top: 200px">
            <p>新增类别：</p>
            <el-input v-model="newCategory" style="margin-top: 10px;">
            </el-input>
            <div style="text-align: right; margin-top: 10px;">
              <el-button @click="submitCategory">添加</el-button>
            </div>
          </el-col>
        </el-row>
      </el-dialog>
    </div>
  </div>
</template>

<script>

import request from "../utils/request";

export default {
  name: 'QuestionManage',
  components: {},
  data() {
    return {
      form: {
        issue: '',
        answer: '',
        category: '',
        type: '',
        userId: '',
        share: '',
        answerList: []
      },
      dialogVisible: false,
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 10,
      tableData: [],
      isLoad: false,
      rules: {
        issue: [
          {required: true, message: '请填写问题', trigger: 'blur'}
        ],
        userId: [
          {required: true, message: '请输入所属用户ID', trigger: 'blur'}
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
      categoryVis: false,
      newCategory: '',
      categoryList: [],

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
      request.get("/question/find", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        if (res.code === '00000') {
          this.tableData = res.data.list;
          this.total = res.data.total;
        }
        else {
          this.$message.error(res.msg);
        }
      })
      request.get("/question/category").then(res => {
        if (res.code === '00000') {
          this.categoryList = res.data;
        }
        else {
          this.$message.error(res.msg);
        }
      })
    },
    addQuestion() {
      this.dialogVisible = true;
      this.form = {}
    },
    addCategory() {
      this.categoryVis = true;
    },
    submitCategory() {
      if (this.newCategory === '') {
        this.$message.error("类别未填写")
      }

      for (let category of this.categoryList) {
        if (category.name === this.newCategory) {
          this.$message.error("当前类别已存在")
          return
        }
      }

      request.post("/question/category/" + this.newCategory).then(res => {
        if (res.code === '00000') {
          this.categoryList.push(this.newCategory);
          this.newCategory = ''
          this.categoryVis = false
          this.$message.success("添加成功");
        } else {
          this.$message({
            type: "error",
            message: res.msg
          });
        }
      })

    },
    saveQuestion() {
      /* 更新 */
      if (this.form.id) {
        request.put("/question/update", this.form).then(res => {
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
        request.post("/question/save", this.form).then(res => {
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
    handleDelete: function (id) {
      request.delete("/question/delete/" + id).then(res => {
        if (res.code === '00000') {
          this.$message({
            type: "success",
            message: "删除成功"
          });
          this.load();
        } else {
          this.$message({
            type: "error",
            message: res.msg
          });
        }
      });
    },
    answerDelete: function (id, row) {
      // axios 的 delete 没有data属性，所以要写到 config 里面
      request.delete("/answer/delete", {data: row}).then(res => {
        if (res.code === '00000') {
          this.$message({
            type: "success",
            message: "删除成功"
          });
          // 移除对应索引位置的数据，可以对row进行设置向后台请求删除数据
          this.answerList.splice(id);

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
