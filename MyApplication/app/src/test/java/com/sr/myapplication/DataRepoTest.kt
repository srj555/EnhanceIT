package com.sr.myapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.sr.myapplication.model.DataModel
import com.sr.myapplication.model.DataRepoModel
import com.sr.myapplication.network.DataRepository
import com.sr.myapplication.network.RetrofitAPIInterface
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class DataRepoTest {
    @Mock
    var webService: RetrofitAPIInterface? = null

    @InjectMocks
    var dataRepository: DataRepository? = null

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dataRepository = Mockito.mock(DataRepository::class.java)
    }

    //Setting how up the mock behaves
    @Test
    fun getList(){
            val data = MutableLiveData<DataRepoModel>()
            val dataModel = arrayListOf(Mockito.spy(DataModel::class.java))
            val dataRepoModel = DataRepoModel(dataModel)
            data.value = dataRepoModel

            //Setting how up the mock behaves
            Mockito.doReturn(data).`when`(dataRepository)?.list
            webService!!.retrieveList()
            Mockito.verify(webService)?.retrieveList()
            Assert.assertEquals(data, dataRepository?.list)
        }
}