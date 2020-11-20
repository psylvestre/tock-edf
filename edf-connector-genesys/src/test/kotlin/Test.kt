import ai.tock.bot.engine.action.SendSentence
import ai.tock.shared.jackson.mapper
import com.fasterxml.jackson.module.kotlin.readValue
import edf.genesys.response.EDFSentence

fun main(args: Array<String>) {
    val chaine: String = "[{\"dialogId\":\"5fb640f00b1e863220438876\",\"playerId\":{\"id\":\"101-1604952834.9740945\",\"type\":\"user\",\"clientId\":null},\"applicationId\":\"bot_edf\",\"recipientId\":{\"id\":\"bot\",\"type\":\"bot\",\"clientId\":null},\"text\":\"Hello my friend\",\"id\":\"5fb640f00b1e863220438875\",\"date\":\"2020-11-19T09:54:56.003Z\",\"state\":{\"entityValues\":[],\"testEvent\":false,\"targetConnectorType\":{\"id\":\"edfallomedia\",\"userInterfaceType\":\"voiceAssistant\"},\"userVerified\":true,\"intent\":\"hello\",\"notification\":false},\"metadata\":{\"lastAnswer\":false,\"priority\":\"normal\",\"visibility\":\"UNKNOWN\",\"replyMessage\":\"UNKNOWN\",\"quoteMessage\":\"UNKNOWN\",\"orchestrationLock\":false},\"hasCustomMessage\":false,\"hasNlpStats\":true,\"messages\":[],\"nlpStats\":{\"locale\":\"fr\",\"intentResult\":{\"name\":\"hello\"},\"entityResult\":[],\"entityResultAfterMerge\":[],\"nlpQuery\":{\"queries\":[\"Hello my friend\"],\"namespace\":\"app\",\"applicationName\":\"bot_edf\",\"context\":{\"language\":\"fr\",\"clientId\":\"101-1604952834.9740945\",\"dialogId\":\"5fb640f00b1e863220438876\",\"clientDevice\":\"edfallomedia\",\"referenceDate\":\"2020-11-19T09:54:56.037Z\",\"referenceTimezone\":\"Europe/Paris\",\"test\":false,\"registerQuery\":true},\"state\":{\"states\":[]},\"intentsSubset\":[]},\"nlpResult\":{\"intent\":\"hello\",\"intentNamespace\":\"app\",\"language\":\"fr\",\"entities\":[],\"notRetainedEntities\":[],\"intentProbability\":0.5,\"entitiesProbability\":1.0,\"retainedQuery\":\"Hello my friend\",\"otherIntentsProbabilities\":{\"app:xxxxxxxxxx\":0.5}}},\"messageLoaded\":true,\"nlpStatsLoaded\":true},{\"playerId\":{\"id\":\"bot\",\"type\":\"bot\",\"clientId\":null},\"applicationId\":\"bot_edf\",\"recipientId\":{\"id\":\"101-1604952834.9740945\",\"type\":\"user\",\"clientId\":null},\"text\":\"Bonjour, Que puis-je faire pour vous aider ?\",\"messages\":[],\"id\":\"5fb640f00b1e863220438878\",\"date\":\"2020-11-19T09:54:56.779Z\",\"state\":{\"entityValues\":[],\"testEvent\":false,\"userVerified\":true,\"notification\":false},\"metadata\":{\"lastAnswer\":true,\"priority\":\"normal\",\"visibility\":\"UNKNOWN\",\"replyMessage\":\"UNKNOWN\",\"quoteMessage\":\"UNKNOWN\",\"orchestrationLock\":false}}]"
    val liste: List<SendSentence> = mapper.readValue(chaine)
    println("${liste.size}")
    liste.forEach {
        val s:SendSentence = it
        when (it.nlpStats) {
            null -> {
                println("Réponse : ${s.text}")
                println("Date     : ${s.date}")
            }
            else -> {
                println("Question : ${s.text}")
                println("Intent   : ${s.nlpStats?.nlpResult?.intent}")
                println("Date     : ${s.date}")
            }
        }
    }
    val results: List<EDFSentence> = liste.map {
        EDFSentence(it.text, it.nlpStats?.nlpResult?.intent, it.date)
    }

    results.forEach {
        println("Texte     : ${it.text}")
        println("Intention : ${it.intent}")
        println("Quand     : ${it.date}")
    }
}