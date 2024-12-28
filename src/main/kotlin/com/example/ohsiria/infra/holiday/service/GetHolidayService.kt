package com.example.ohsiria.infra.holiday.service

import com.example.ohsiria.domain.holiday.entity.Holiday
import com.example.ohsiria.domain.holiday.repository.HolidayRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.xml.sax.InputSource
import java.io.StringReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.xml.parsers.DocumentBuilderFactory

import org.w3c.dom.Element
import org.w3c.dom.NodeList
import javax.xml.parsers.DocumentBuilder

@Service
class GetHolidayService(
    @Value("\${holiday.api.url}") private val holidayUrl: String,
    @Value("\${holiday.api.key}") private val holidayKey: String,
    private val holidayRepository: HolidayRepository
) {
    private val restTemplate = RestTemplate()

    fun updateHolidays(year: Int) {
        val holidays = getHolidaysFromApi(year)
        holidayRepository.saveAll(holidays)
    }

    private fun getHolidaysFromApi(year: Int): List<Holiday> {
        val url = "$holidayUrl?serviceKey=$holidayKey&solYear=$year&numOfRows=100"
        val response = restTemplate.getForObject<String>(url)

        val factory = DocumentBuilderFactory.newInstance()
        val builder: DocumentBuilder = factory.newDocumentBuilder()
        val doc = builder.parse(InputSource(StringReader(response)))

        val nodeList: NodeList = doc.getElementsByTagName("item")
        return (0 until nodeList.length).map { i ->
            val item = nodeList.item(i) as Element
            val locdate = item.getElementsByTagName("locdate").item(0).textContent
            val dateName = item.getElementsByTagName("dateName").item(0).textContent
            val date = LocalDate.parse(locdate, DateTimeFormatter.BASIC_ISO_DATE)
            Holiday(date, dateName)
        }
    }

    fun isHoliday(date: LocalDate): Boolean {
        return holidayRepository.existsById(date)
    }
}
