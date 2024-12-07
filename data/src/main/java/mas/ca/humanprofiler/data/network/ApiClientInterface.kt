package mas.ca.humanprofiler.data.network

import mas.ca.humanprofiler.data.datasources.remote.AgifyService

interface ApiClientInterface {
    val agifyService: AgifyService
}