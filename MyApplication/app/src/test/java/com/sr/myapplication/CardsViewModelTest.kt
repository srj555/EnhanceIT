package com.sr.myapplication


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.sr.myapplication.model.DataModel
import com.sr.myapplication.model.DataRepoModel
import com.sr.myapplication.network.DataRepository
import com.sr.myapplication.network.RetrofitAPIInterface
import com.sr.myapplication.viewmodel.CardsListViewModel
import junit.framework.Assert.assertEquals
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


@RunWith(MockitoJUnitRunner::class)
class CardsListViewModelTest {
    @InjectMocks
    var viewModel: CardsListViewModel? = null

    @Mock
    var repository: DataRepository? = null

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    var mockWebServer: MockWebServer? = null
    var retrofit: Retrofit? = null
    var service: RetrofitAPIInterface? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mockWebServer = MockWebServer()
        retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer?.url("").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit!!.create(RetrofitAPIInterface::class.java)
        viewModel = Mockito.mock(CardsListViewModel::class.java)
    }

    @Test
    fun fetchList() {
        val dataModel = arrayListOf(Mockito.spy(DataModel::class.java))
        val dataRepoModel = DataRepoModel(dataModel)
        val data = MutableLiveData<DataRepoModel>()
        data.postValue(dataRepoModel)

        //Setting how up the mock behaves
        Mockito.doReturn(data).`when`(viewModel)?.getListObservable()
        //
        repository!!.list
        Mockito.verify(repository)?.list
        Assert.assertEquals(data, viewModel!!.getListObservable())
    }

    @Test
    @Throws(IOException::class)
    fun testApiSuccess() {
        mockWebServer?.enqueue(
            MockResponse().setBody(
                "[\n" +
                        "  {\n" +
                        "    \"fairings\": {\n" +
                        "      \"reused\": false,\n" +
                        "      \"recovery_attempt\": false,\n" +
                        "      \"recovered\": false,\n" +
                        "      \"ships\": []\n" +
                        "    },\n" +
                        "    \"links\": {\n" +
                        "      \"flickr\": {\n" +
                        "        \"small\": [],\n" +
                        "        \"original\": []\n" +
                        "      },\n" +
                        "      \"presskit\": null,\n" +
                        "      \"webcast\": \"https://www.youtube.com/watch?v=0a_00nJ_Y88\",\n" +
                        "      \"youtube_id\": \"0a_00nJ_Y88\",\n" +
                        "      \"article\": \"https://www.space.com/2196-spacex-inaugural-falcon-1-rocket-lost-launch.html\",\n" +
                        "      \"wikipedia\": \"https://en.wikipedia.org/wiki/DemoSat\"\n" +
                        "    },\n" +
                        "    \"static_fire_date_utc\": \"2006-03-17T00:00:00.000Z\",\n" +
                        "    \"static_fire_date_unix\": 1142553600,\n" +
                        "    \"crew\": [],\n" +
                        "    \"ships\": [],\n" +
                        "    \"capsules\": [],\n" +
                        "    \"payloads\": [\n" +
                        "      \"5eb0e4b5b6c3bb0006eeb1e1\"\n" +
                        "    ],\n" +
                        "    \"launchpad\": \"5e9e4502f5090995de566f86\",\n" +
                        "    \"auto_update\": true,\n" +
                        "    \"flight_number\": 1,\n" +
                        "    \"name\": \"FalconSat\",\n" +
                        "    \"upcoming\": false,\n" +
                        "    \"id\": \"5eb87cd9ffd86e000604b32a\"\n" +
                        "  }\n" +
                        "]"
            )
        )
        val call= service!!.retrieveList()
        val dataModel = call!!.execute()
        Assert.assertNotNull(dataModel)
        assertEquals("FalconSat", dataModel.body()[0]?.name)
    }

    @Test
    @Throws(IOException::class)
    fun testApiFailure() {
        mockWebServer?.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody("[]")
        )
        val call= service!!.retrieveList()
        val dataModel = call!!.execute()
        Assert.assertNull(dataModel.body())
    }

    @After
    fun tearDown() {
        //Finish web server
        try {
            mockWebServer?.shutdown()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
