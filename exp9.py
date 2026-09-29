import plotly.express as px
import pandas as pd
data={
    "Year":[2018,2019,2020,2021,2022]*2,
    "Temperature":[30,32,33,31,34,28,29,31,20,33],
    "City":["Chennai"]*5+["Delhi"]*5
}
df=pd.DataFram(data)
df=df.sort_values(by="Year")
pig=px.line(df,x="year",y="Temperature",color="City",
            markers=True,title="Temperature Trends Over Year")
fig.update_layout(
    xaxis=dict(dtick=1),
    yaxis_titl="Temperature(°C)",
    xaxis_title="Year",
    transition={'duration':500}
)
fig.show()