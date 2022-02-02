import React, { useState } from "react"
import { StatusBar } from "expo-status-bar"
import {
  StyleSheet,
  Text,
  View,
  TextInput,
  Button,
  ScrollView,
  FlatList,
} from "react-native"

import GoalItem from "./src/components/GoalItem"
import GoalInput from "./src/components/GoalInput"

export default function App() {
  const [enteredGoal, setEnteredGoal] = useState("")
  const [courseGoals, setCourseGoals] = useState([])
  const awskey = "sdasdsfg349fdlss/342"
  const credentials = "sdasdsfg349fdlss/342"

  const goalInputHandler = (enteredText) => {
    setEnteredGoal(enteredText)
  }

  const addGoalHandler = () => {
    setCourseGoals((currentGoals) => [
      ...currentGoals,
      { key: Math.random().toString(), value: enteredGoal },
    ])
  }
  return (
    <View style={styles.screen}>
      <GoalInput
        goalInputHandler={goalInputHandler}
        enteredGoal={enteredGoal}
        addGoalHandler={addGoalHandler}
      />
      <View>
        <FlatList
          data={courseGoals}
          renderItem={(itemData) => <GoalItem title={itemData.item.value} />}
        ></FlatList>
      </View>
    </View>
  )
}

const styles = StyleSheet.create({
  screen: {
    padding: 50,
  },
})
