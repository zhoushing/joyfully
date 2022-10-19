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

    <el-scrollbar max-height="68vh" style="height: 68vh">

      <!--加上border就会表单溢出-->
      <el-table
          :data="tableData"
          style="width: 98%"
          stripe>
        <el-table-column type="expand">
          <template #default="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="问题">
                <span>{{ props.row.issue }}</span>
              </el-form-item>
              <br />
              <el-form-item>
                <el-button size="mini" type="success" plain @click="showAnswer(props.row.answerList)">
                  查看答案列表
                </el-button>
                <el-button size="mini" type="primary" plain @click="addAnswer(props.row)">
                  添加答案
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
            label="问题分类">
          <template #default="scope">
            <el-tag v-for="c in scope.row.categoryList" :key="c.id" :type="'primary'">
              {{c.name}}
            </el-tag>
          </template>
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
            prop="attentionCount"
            label="关注数">
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
          <el-table-column prop="praiseCount" label="点赞数"> </el-table-column>
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

      <el-dialog center title="添加答案" v-model="addAnswerVis" width="40%">
          <template #default="scope">
            <el-form >
              <el-form-item>
                问题：<br />{{nowQuestion}}
              </el-form-item>
              <el-form-item>
                你的答案（已有的答案会被覆盖）：
                <el-input type="textarea" autosize v-model="answerContent" style="width: 98%"></el-input>
                <br />
              </el-form-item>
              <el-form-item>
                <el-button type="success" size="mini" @click="submitAnswer">添加</el-button>
              </el-form-item>
            </el-form>
          </template>
      </el-dialog>

      <el-dialog center title="修改问题" v-model="dialogVisible" width="30%" @close="questionUpdateClose">
        <el-form ref="form" :model="form" label-width="120px" :rules="rules">
          <el-form-item label="问题分类" prop="category">
            <el-tag closable @close="deleteCategory(category.id, form.id, index)"
                    v-for="(category, index) in form.categoryList" :key="category.id" :type="'primary'">
              {{category.name}}
            </el-tag>
            <p>
              <el-select v-model="newCategory" placeholder="新增类别">
                <el-option v-for="(item, index) in categoryList" :label="item.name"
                           :value="item.name" :disabled="categoryRepeat(form.categoryList, item.name)"></el-option>
              </el-select>
            </p>
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

import request from "../utils/request";

export default {
  name: 'MyQuestion',
  components: {},
  data() {
    return {
      form: {
        id: '',
        issue: '',
        answerList: [],
        categoryList: [],
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
      addAnswerVis: false,
      answerContent: '',
      nowQuestion: '',
      nowQuestionId: '',
      categoryList: [],
      newCategory: '',

    }
  },
  /* 初始加载方法 */
  created() {
    this.load();
  },
  methods: {
    load() {
      request.get("/question/findByUserId", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
        }
      }).then(res => {
        this.tableData = res.data.list;
        this.total = res.data.total;
      });
      request.get("/question/category").then(res => {
        if (res.code === '00000') {
          this.categoryList = res.data;
        }
        else {
          this.$message.error(res.msg);
        }
      })
    },
    addQuestion: function () {
      this.dialogVisible = true;
      this.form.id = '';
      this.form.issue = '';
      this.form.answerList = [];
      this.form.categoryList = [];
      this.form.type = '';
    },
    saveQuestion() {
      /* 更新 */
      if (this.form.id) {
        if (this.newCategory !== '') {
          let id

          for (let category of this.categoryList) {
            if (category.name === this.newCategory) {
              id = category.id
            }
          }

          let temp = {
            id: id,
            name: this.newCategory
          }
          this.form.categoryList.push(temp)
        }

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
    addAnswer(row) {
      this.nowQuestion = row.issue
      this.nowQuestionId = row.id

      this.addAnswerVis = true;
    },
    submitAnswer() {
      if (this.answerContent === '') {
        this.$message({
          type: "error",
          message: "答案不能为空"
        });
        return ;
      }

      let answer = {}

      answer.questionId = this.nowQuestionId
      answer.content = this.answerContent

      request.post("/answer/save", answer).then(res => {
        if (res.code === '00000') {
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
      })

      this.answerContent = ''
      this.nowQuestion = ''
      this.nowQuestionId = ''
      this.addAnswerVis = false;

      this.load()
    },
    questionUpdateClose() {
      this.newCategory = ''
    },
    categoryRepeat(list, name) {
      for (let item of list) {
        if (item.name === name) {
          return true
        }
      }
      return false
    },
    deleteCategory(categoryId, questionId, index) {
      if (this.form.categoryList.length === 1) {
        this.$message.error("至少保留一个类别")
        return
      }

      let questionCategory = {
        questionId: questionId,
        categoryId: categoryId
      }

      // axios 的 delete 没有data属性，所以要写到 config 里面
      request.delete("/question/category", {
        data: questionCategory
      }).then(res => {
        if (res.code === '00000') {
          this.form.categoryList.splice(index, 1)
        }
        else {
          this.$message.error(res.msg)
        }
      })
    }
  },
  computed: {

  }
}
</script>
